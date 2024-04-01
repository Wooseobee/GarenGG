package gg.garen.back.championRecommendation.service;

import gg.garen.back.championRecommendation.dto.request.RequestPredictNotDto;
import gg.garen.back.championRecommendation.dto.response.ResponseGetChampionRecommendationDto;
import gg.garen.back.common.domain.mongo.*;
import gg.garen.back.common.domain.mysql.ApiKey;
import gg.garen.back.common.domain.mysql.PlayerInfoTest;
import gg.garen.back.common.repository.PlayerInfoTestRepository;
import gg.garen.back.common.service.ApiKeyUtils;
import lombok.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChampionRecommendationServiceImpl implements ChampionRecommendationService {

    private final ApiKeyUtils apiKeyUtils;
    private final PlayerInfoTestRepository playerInfoTestRepository;
    private final AsyncService asyncService;

    @Override
    public ResponseEntity<?> getChampionRecommendation(String summonerName, String tagLine) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        StringBuilder sb;
        RestTemplate restTemplate = new RestTemplate();
        ResponseGetChampionRecommendationDto responseGetChampionRecommendationDto = new ResponseGetChampionRecommendationDto();

        ApiKey apiKey;
        String url;
        AccountDto receivedAccountDto;
        List<String> receivedMatchIds;
        List<MatchInfo> receivedMatchInfos = new ArrayList<>();
        HashMap<String, WinLose> newPlayer = new HashMap<>();

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date()))
                .append(" ").append(summonerName).append("-").append(tagLine).append(" 시작");
        System.out.println(sb);

        while (true) {
            try {
                apiKey = apiKeyUtils.getOneApiKey();

                url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{summonerName}/{tagLine}?api_key={apiKey}";
                receivedAccountDto = restTemplate.getForObject(url, AccountDto.class, summonerName, tagLine, apiKey.getApiKey());

                url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + receivedAccountDto.getPuuid() + "/ids?queue=420&count=98&api_key=" + apiKey.getApiKey();
                receivedMatchIds = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                }).getBody();

                break;
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {

                } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    responseGetChampionRecommendationDto.setErrorMessage("없는 유저입니다");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
                } else {
                    responseGetChampionRecommendationDto.setErrorMessage("receivedAccountDto 에러1");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
                }
            } catch (Exception e) {
                responseGetChampionRecommendationDto.setErrorMessage("receivedAccountDto 에러2");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
            }
        }

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date()))
                .append(" receivedMatchIds.size() = ").append(receivedMatchIds.size());
        System.out.println(sb);

        final int CHUNK_SIZE = 5;
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 리스트를 5개씩 잘라서 처리
        for (int i = 0; i < receivedMatchIds.size(); i += CHUNK_SIZE) {
            // 서브 리스트 생성
            List<String> subList = receivedMatchIds.subList(i, Math.min(receivedMatchIds.size(), i + CHUNK_SIZE));

            // 서브 리스트의 각 항목에 대한 비동기 작업 생성 및 수행
            List<CompletableFuture<Void>> subFutures = subList.stream()
                    .map(receiveMatchId -> asyncService.fillReceivedMatchInfos(receivedMatchInfos, receiveMatchId))
                    .collect(Collectors.toList());

            // CompletableFuture.allOf()를 사용하여 모든 비동기 작업이 완료될 때까지 기다림
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(subFutures.toArray(new CompletableFuture[0]));
            futures.add(combinedFuture);
        }

        // 모든 청크의 비동기 작업이 완료될 때까지 기다림
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date()))
                .append(" receivedMatchInfos.size() = ").append(receivedMatchInfos.size());
        System.out.println(sb);

        for (MatchInfo matchInfo : receivedMatchInfos) {
            for (Participant participant : matchInfo.getInfo().getParticipants()) {

                if (participant.getRiotIdGameName() == null) {
                    if (participant.getSummonerName().equals(receivedAccountDto.getGameName())) {
                    } else {
                        continue;
                    }
                } else {
                    if (participant.getRiotIdGameName().equals(receivedAccountDto.getGameName())
                            && participant.getRiotIdTagline().equals(receivedAccountDto.getTagLine())) {
                    } else {
                        continue;
                    }
                }

                if (!newPlayer.containsKey(participant.getChampionName()))
                    newPlayer.put(participant.getChampionName(), new WinLose());
                if (participant.isWin())
                    newPlayer.get(participant.getChampionName()).setWin(newPlayer.get(participant.getChampionName()).getWin() + 1);
                else
                    newPlayer.get(participant.getChampionName()).setLose(newPlayer.get(participant.getChampionName()).getLose() + 1);

            }
        }
        RequestPredictNotDto requestPredictNotDto = new RequestPredictNotDto();

        // tier
        PlayerInfoTest findedPlayerInfoTest = playerInfoTestRepository.findBySummonerNameAndTagLine(receivedAccountDto.getGameName(), receivedAccountDto.getTagLine());
        if (findedPlayerInfoTest == null) {
            requestPredictNotDto.setTier("UNRANKED");
        } else {
            requestPredictNotDto.setTier(findedPlayerInfoTest.getTier());
        }

        // mostDatas
        ArrayList<RequestPredictNotDto.MostData> mostDatas = new ArrayList<>();
        for (String championName : newPlayer.keySet()) {
            RequestPredictNotDto.MostData mostData = new RequestPredictNotDto.MostData();

            // champion
            mostData.setChampion(championName);

            // game
            int win = newPlayer.get(championName).getWin();
            int lose = newPlayer.get(championName).getLose();
            int percentage = win * 100 / (win + lose);
            String game = "";
            if (win != 0)
                game = game + win + "W";
            if (lose != 0)
                game = game + lose + "L";
            game = game + percentage + "%";
            mostData.setGame(game);

            // add
            mostDatas.add(mostData);
        }

        // mostDatas 판수로 정렬
        Collections.sort(mostDatas, new Comparator<RequestPredictNotDto.MostData>() {
            @Override
            public int compare(RequestPredictNotDto.MostData o1, RequestPredictNotDto.MostData o2) {
                Pattern pattern = Pattern.compile("(\\d+)([WL])");

                int o1sum = 0;
                int o2sum = 0;
                Matcher matcher;

                matcher = pattern.matcher(o1.getGame());
                while (matcher.find()) {
                    String number = matcher.group(1);
                    String letter = matcher.group(2);

                    switch (letter) {
                        case "W", "L":
                            o1sum += Integer.parseInt(number);
                            break;
                    }
                }
                matcher = pattern.matcher(o2.getGame());
                while (matcher.find()) {
                    String number = matcher.group(1);
                    String letter = matcher.group(2);

                    switch (letter) {
                        case "W", "L":
                            o2sum += Integer.parseInt(number);
                            break;
                    }
                }

                if (o1sum > o2sum) {
                    return -1;
                } else if (o1sum == o2sum)
                    return 0;
                else
                    return 1;
            }
        });

        // mostSeq
        for (int i = 0; i < mostDatas.size(); i++)
            mostDatas.get(i).setMostSeq(String.valueOf(i + 1));

        // mostDatas 완성
        requestPredictNotDto.setMostDatas(mostDatas.subList(0, Math.min(10, mostDatas.size())));

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date()))
                .append(" requestPredictNotDto 완성");
        System.out.println(sb);

        if (requestPredictNotDto.getMostDatas().isEmpty()) {
            responseGetChampionRecommendationDto.setErrorMessage("솔로 랭크 전적이 없습니다");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
        }

        try {
            url = "http://j10a605.p.ssafy.io:8000/api/predict/not";

            ResponseEntity<List<ResponseGetChampionRecommendationDto.ChampionRecommendationData>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(requestPredictNotDto),
                    new ParameterizedTypeReference<List<ResponseGetChampionRecommendationDto.ChampionRecommendationData>>() {
                    });

            sb = new StringBuilder();
            sb.append("현재 시간: ").append(formatter.format(new Date()))
                    .append(" 성공");
            System.out.println(sb);

            responseGetChampionRecommendationDto.setChampionRecommendationDatas(response.getBody());
            responseGetChampionRecommendationDto.setTier(requestPredictNotDto.getTier());
            responseGetChampionRecommendationDto.setMostDatas(requestPredictNotDto.getMostDatas());
            return ResponseEntity.status(HttpStatus.OK).body(responseGetChampionRecommendationDto);
        } catch (Exception e) {
            sb = new StringBuilder();
            sb.append("현재 시간: ").append(formatter.format(new Date()))
                    .append(" api/predict/not 오류");
            System.out.println(sb);

            responseGetChampionRecommendationDto.setErrorMessage("api/predict/not 오류");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
        }
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class AccountDto {
        private String puuid;
        private String gameName;
        private String tagLine;
    }

    @Builder
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WinLose {
        private Integer win = 0;
        private Integer lose = 0;
    }

}


