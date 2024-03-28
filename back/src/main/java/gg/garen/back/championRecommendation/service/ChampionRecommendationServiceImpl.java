package gg.garen.back.championRecommendation.service;

import gg.garen.back.championRecommendation.dto.request.RequestPredictDto;
import gg.garen.back.championRecommendation.dto.request.RequestPredictNotDto;
import gg.garen.back.championRecommendation.dto.response.ResponseGetChampionRecommendationDto;
import gg.garen.back.common.domain.mongo.*;
import gg.garen.back.common.domain.mysql.ApiKey;
import gg.garen.back.common.domain.mysql.PastSeasonPlayerInfo;
import gg.garen.back.common.domain.mysql.PlayerInfo;
import gg.garen.back.common.repository.PastSeasonPlayerInfoRepository;
import gg.garen.back.common.repository.PastSeasonPlayerMostRepository;
import gg.garen.back.common.repository.PlayerInfoRepository;
import gg.garen.back.common.repository.PlayerMostRepository;
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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ChampionRecommendationServiceImpl implements ChampionRecommendationService {

    private final PlayerInfoRepository playerInfoRepository;
    private final PlayerMostRepository playerMostRepository;
    private final PastSeasonPlayerInfoRepository pastSeasonPlayerInfoRepository;
    private final PastSeasonPlayerMostRepository pastSeasonPlayerMostRepository;
    private final ApiKeyUtils apiKeyUtils;

    @Override
    public ResponseEntity<?> getChampionRecommendation(String summonerName, String tagLine) {

        ResponseGetChampionRecommendationDto responseGetChampionRecommendationDto = new ResponseGetChampionRecommendationDto();

        PlayerInfo findedPlayerInfo;
        PastSeasonPlayerInfo findedPastSeasonPlayerInfo;

        ApiKey apiKey;
        String url;
        AccountDto receivedAccountDto;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<String>> response;
        List<String> receivedMatchIds;
        List<MatchInfo> receivedMatchInfos = null;
        MatchInfo receivedMatchInfo;
        HashMap<String, WinLose> player;

        // playerInfo에서 찾아
        findedPlayerInfo = playerInfoRepository.findBySummonerNameAndTagLine(summonerName, tagLine);

        // playerInfo에 없다
        if (findedPlayerInfo == null) {

            // pastSeasonPlayerInfo에서 찾아
            findedPastSeasonPlayerInfo = pastSeasonPlayerInfoRepository.findBySummonerNameAndTagLine(summonerName, tagLine);

            // pastSeasonPlayerInfo에 없다 => 최초 검색 닉네임이다.
            if (findedPastSeasonPlayerInfo == null) {
                // puuid 받기
                while (true) {
                    try {
                        apiKey = apiKeyUtils.getOneApiKey();
                        url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{summonerName}/{tagLine}?api_key={apiKey}";
                        receivedAccountDto = restTemplate.getForObject(url, AccountDto.class, summonerName, tagLine, apiKey.getApiKey());
                        break;
                    } catch (HttpClientErrorException e) {
                        // 키 폭발
                        if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                            System.out.println("receivedAccountDto 받기 " + e.getStatusText());
                        }
                        // 없는 유저
                        else if (e.getStatusCode() == HttpStatus.BAD_REQUEST || e.getStatusCode() == HttpStatus.NOT_FOUND) {
                            System.out.println("receivedAccountDto 받기 " + e.getStatusText());
                            responseGetChampionRecommendationDto.setErrorMessage("없는 유저입니다");
                            return ResponseEntity.status(e.getStatusCode()).body(responseGetChampionRecommendationDto);
                        }
                        // 기타 다른 HTTP 에러
                        else {
                            System.out.println("receivedAccountDto 받기 " + e.getStatusText());
                            responseGetChampionRecommendationDto.setErrorMessage(e.getStatusText());
                            return ResponseEntity.status(e.getStatusCode()).body(responseGetChampionRecommendationDto);
                        }
                    } catch (Exception e) {
                        System.out.println("receivedAccountDto 받기 " + e.getMessage());
                        responseGetChampionRecommendationDto.setErrorMessage(e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
                    }
                }

                PastSeasonPlayerInfo newPastSeasonPlayerInfo = new PastSeasonPlayerInfo();
                newPastSeasonPlayerInfo.setApiKeyId(apiKey.getId());
                newPastSeasonPlayerInfo.setPuuid(receivedAccountDto.getPuuid());
                newPastSeasonPlayerInfo.setSummonerName(receivedAccountDto.getGameName());
                newPastSeasonPlayerInfo.setTier("Unranked");
                newPastSeasonPlayerInfo.setTagLine(receivedAccountDto.getTagLine());

                System.out.println("newPastSeasonPlayerInfo.getApiKeyId() = " + newPastSeasonPlayerInfo.getApiKeyId());
                System.out.println("newPastSeasonPlayerInfo.getPuuid() = " + newPastSeasonPlayerInfo.getPuuid());
                System.out.println("newPastSeasonPlayerInfo.getSummonerName() = " + newPastSeasonPlayerInfo.getSummonerName());
                System.out.println("newPastSeasonPlayerInfo.getTagLine() = " + newPastSeasonPlayerInfo.getTagLine());
                System.out.println("newPastSeasonPlayerInfo.getTier() = " + newPastSeasonPlayerInfo.getTier());
                System.out.println("newPastSeasonPlayerInfo 성공");
                pastSeasonPlayerInfoRepository.save(newPastSeasonPlayerInfo);

                // matchIds 받기
                try {
                    apiKey = apiKeyUtils.getSpecificApiKey(newPastSeasonPlayerInfo.getApiKeyId());
                    url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + newPastSeasonPlayerInfo.getPuuid() + "/ids?queue=420&count=98&api_key=" + apiKey.getApiKey();
                    response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                    });
                    receivedMatchIds = response.getBody();
                    System.out.println("receivedMatchIds.size() = " + receivedMatchIds.size());
                } catch (HttpClientErrorException e) {
                    // 키 폭발
                    if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                        System.out.println("receivedMatchIds 받기 " + e.getStatusText());
                        responseGetChampionRecommendationDto.setErrorMessage("죄송합니다 최소 2분 후 재시도 부탁드립니다");
                        return ResponseEntity.status(e.getStatusCode()).body(responseGetChampionRecommendationDto);
                    }
                    // 기타 다른 HTTP 에러
                    else {
                        responseGetChampionRecommendationDto.setErrorMessage(e.getStatusText());
                        return ResponseEntity.status(e.getStatusCode()).body(responseGetChampionRecommendationDto);
                    }
                } catch (Exception e) {
                    System.out.println("receivedMatchIds 받기 " + e.getMessage());
                    responseGetChampionRecommendationDto.setErrorMessage(e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
                }

                // receivedMatchInfo 받기
                while (true) {
                    try {
                        receivedMatchInfos = new ArrayList<>();
                        url = "https://asia.api.riotgames.com/lol/match/v5/matches/{match}?api_key={apiKey}";
                        for (String receiveMatchId : receivedMatchIds) {
                            receivedMatchInfo = restTemplate.getForObject(url, MatchInfo.class, receiveMatchId, apiKeyUtils.getOneApiKey().getApiKey());
                            receivedMatchInfos.add(receivedMatchInfo);
                        }
                        break;
                    } catch (HttpClientErrorException e) {
                        // 키 폭발
                        if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                            System.out.println("receivedMatchInfo = " + e.getStatusText());
                        }
                        // 더 이상 정보가 없는 게임
                        else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                            System.out.println("receivedMatchInfo = " + e.getStatusText());
                            break;
                        }
                        // 기타 다른 HTTP 에러
                        else {
                            System.out.println("receivedMatchInfo = " + e.getStatusText());
                            responseGetChampionRecommendationDto.setErrorMessage(e.getStatusText());
                            return ResponseEntity.status(e.getStatusCode()).body(responseGetChampionRecommendationDto);
                        }
                    } catch (Exception e) {
                        System.out.println("receivedMatchInfo = " + e.getMessage());
                        responseGetChampionRecommendationDto.setErrorMessage(e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
                    }
                }
                System.out.println("receivedMatchInfos.size() = " + receivedMatchInfos.size());

                // receivedMatchInfos로 playerMost 만들기
                player = new HashMap<>();

                // 매치 경기 하나씩
                int findCount = 0;
                for (MatchInfo playerMatch : receivedMatchInfos) {
                    // 참가자 한명 씩
                    for (Participant participant : playerMatch.getInfo().getParticipants()) {
                        // 나를 찾자
                        if (participant.getRiotIdGameName() == null) {
                            if (participant.getSummonerName().equals(newPastSeasonPlayerInfo.getSummonerName())) {
                            } else {
                                continue;
                            }
                        } else {
                            if (participant.getRiotIdGameName().equals(newPastSeasonPlayerInfo.getSummonerName())
                                    && participant.getRiotIdTagline().equals(newPastSeasonPlayerInfo.getTagLine())) {
                            } else {
                                continue;
                            }
                        }

                        findCount++;

                        if (!player.containsKey(participant.getChampionName()))
                            player.put(participant.getChampionName(), new WinLose());
                        if (participant.isWin())
                            player.get(participant.getChampionName()).setWin(player.get(participant.getChampionName()).getWin() + 1);
                        else
                            player.get(participant.getChampionName()).setLose(player.get(participant.getChampionName()).getLose() + 1);

                    }
                }
                System.out.println("findCount = " + findCount);

                // playerMost 완성하기
                PastSeasonPlayerMost newPastSeasonPlayerMost = new PastSeasonPlayerMost();

                // playerId
                newPastSeasonPlayerMost.setPlayerId(newPastSeasonPlayerInfo.getPlayerId());

                // mostDatas
                ArrayList<MostData> mostDatas = new ArrayList<>();
                for (String championName : player.keySet()) {
                    MostData mostData = new MostData();

                    // champion
                    mostData.setChampion(championName);

                    // game
                    int win = player.get(championName).getWin();
                    int lose = player.get(championName).getLose();
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
                Collections.sort(mostDatas, new Comparator<MostData>() {
                    @Override
                    public int compare(MostData o1, MostData o2) {
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
                newPastSeasonPlayerMost.setMostDatas(mostDatas);

                pastSeasonPlayerMostRepository.save(newPastSeasonPlayerMost);

                System.out.println("newPastSeasonPlayerMost.getPlayerId() = " + newPastSeasonPlayerMost.getPlayerId());
                System.out.println("newPastSeasonPlayerMost.getMostDatas().size() = " + newPastSeasonPlayerMost.getMostDatas().size());
                for (MostData mostData : newPastSeasonPlayerMost.getMostDatas())
                    System.out.println(mostData.getMostSeq() + " " + mostData.getChampion() + " " + mostData.getGame());
                System.out.println("newPastSeasonPlayerMost 성공");

                if (newPastSeasonPlayerMost.getMostDatas().isEmpty()) {
                    System.out.println("newPastSeasonPlayerMost.getMostDatas().isEmpty()");
                    responseGetChampionRecommendationDto.setErrorMessage("솔로 랭크 전적이 없습니다");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseGetChampionRecommendationDto);
                }

                try {
                    url = "https://j10a605.p.ssafy.io:8000/api/predict/not";
                    RequestPredictNotDto requestPredictNotDto = new RequestPredictNotDto();
                    requestPredictNotDto.setPlayerId(newPastSeasonPlayerInfo.getPlayerId());
                    requestPredictNotDto.setTier(newPastSeasonPlayerInfo.getTier());
                    requestPredictNotDto.setMostDatas(newPastSeasonPlayerMost.getMostDatas());

                    ResponseEntity<ResponseGetChampionRecommendationDto> response2 = restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            new HttpEntity<>(requestPredictNotDto),
                            ResponseGetChampionRecommendationDto.class);

                    responseGetChampionRecommendationDto = response2.getBody();

                    System.out.println("성공");
                    return ResponseEntity.status(HttpStatus.OK).body(responseGetChampionRecommendationDto);
                } catch (Exception e) {
                    System.out.println("api/predict/not 오류 " + e.getMessage());
                    responseGetChampionRecommendationDto.setErrorMessage("api/predict/not 오류");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
                }

            }
            // pastSeasonPlayerInfo에 있다 => 이전에 검색했던 닉네임이다.
            else {
                System.out.println("findedPastSeasonPlayerInfo.getPlayerId() = " + findedPastSeasonPlayerInfo.getPlayerId());
                System.out.println("findedPastSeasonPlayerInfo.getApiKeyId() = " + findedPastSeasonPlayerInfo.getApiKeyId());
                System.out.println("findedPastSeasonPlayerInfo.getPuuid() = " + findedPastSeasonPlayerInfo.getPuuid());
                System.out.println("findedPastSeasonPlayerInfo.getSummonerName() = " + findedPastSeasonPlayerInfo.getSummonerName());
                System.out.println("findedPastSeasonPlayerInfo.getTagLine() = " + findedPastSeasonPlayerInfo.getTagLine());
                System.out.println("findedPastSeasonPlayerInfo.getTier() = " + findedPastSeasonPlayerInfo.getTier());
                System.out.println("findedPastSeasonPlayerInfo 성공");

                PastSeasonPlayerMost findedPastSeasonPlayerMost = pastSeasonPlayerMostRepository.findById(findedPastSeasonPlayerInfo.getPlayerId()).orElse(null);

                System.out.println("findedPastSeasonPlayerMost.getPlayerId() = " + findedPastSeasonPlayerMost.getPlayerId());
                System.out.println("findedPastSeasonPlayerMost.getMostDatas().size() = " + findedPastSeasonPlayerMost.getMostDatas().size());
                for (MostData mostData : findedPastSeasonPlayerMost.getMostDatas())
                    System.out.println(mostData.getMostSeq() + " " + mostData.getChampion() + " " + mostData.getGame());
                System.out.println("findedPastSeasonPlayerMost 성공");

                if (findedPastSeasonPlayerMost.getMostDatas().isEmpty()) {
                    System.out.println("findedPastSeasonPlayerMost.getMostDatas().isEmpty()");
                    responseGetChampionRecommendationDto.setErrorMessage("솔로 랭크 전적이 없습니다");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseGetChampionRecommendationDto);
                }

                try {
                    url = "https://j10a605.p.ssafy.io:8000/api/predict/not";
                    RequestPredictNotDto requestPredictNotDto = new RequestPredictNotDto();
                    requestPredictNotDto.setPlayerId(findedPastSeasonPlayerInfo.getPlayerId());
                    requestPredictNotDto.setTier(findedPastSeasonPlayerInfo.getTier());
                    requestPredictNotDto.setMostDatas(findedPastSeasonPlayerMost.getMostDatas());

                    ResponseEntity<ResponseGetChampionRecommendationDto> response2 = restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            new HttpEntity<>(requestPredictNotDto),
                            ResponseGetChampionRecommendationDto.class);

                    responseGetChampionRecommendationDto = response2.getBody();

                    System.out.println("성공");
                    return ResponseEntity.status(HttpStatus.OK).body(responseGetChampionRecommendationDto);
                } catch (Exception e) {
                    System.out.println("api/predict/not 오류 " + e.getMessage());
                    responseGetChampionRecommendationDto.setErrorMessage("api/predict/not 오류");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
                }
            }

        }
        // playerInfo에 있다
        else {
            System.out.println("findedPlayerInfo.getPlayerId() = " + findedPlayerInfo.getPlayerId());
            System.out.println("findedPlayerInfo.getApiKeyId() = " + findedPlayerInfo.getApiKeyId());
            System.out.println("findedPlayerInfo.getPuuid() = " + findedPlayerInfo.getPuuid());
            System.out.println("findedPlayerInfo.getSummonerName() = " + findedPlayerInfo.getSummonerName());
            System.out.println("findedPlayerInfo.getTagLine() = " + findedPlayerInfo.getTagLine());
            System.out.println("findedPlayerInfo.getTier() = " + findedPlayerInfo.getTier());
            System.out.println("findedPlayerInfo 성공");

            PlayerMost findedPlayerMost = playerMostRepository.findById(findedPlayerInfo.getPlayerId()).orElse(null);
            if (findedPlayerMost == null) {
                System.out.println("findedPlayerMost 동기화 오류");
                responseGetChampionRecommendationDto.setErrorMessage("findedPlayerMost 동기화 오류!!!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
            }

            System.out.println("findedPlayerMost.getPlayerId() = " + findedPlayerMost.getPlayerId());
            System.out.println("findedPlayerMost.getMostDatas().size() = " + findedPlayerMost.getMostDatas().size());
            for (MostData mostData : findedPlayerMost.getMostDatas())
                System.out.println(mostData.getMostSeq() + " " + mostData.getChampion() + " " + mostData.getGame());
            System.out.println("findedPlayerMost 성공");

            try {
                url = "https://j10a605.p.ssafy.io:8000/api/predict";
                RequestPredictDto requestPredictDto = new RequestPredictDto();
                requestPredictDto.setPlayerId(findedPlayerInfo.getPlayerId());
                requestPredictDto.setTier(findedPlayerInfo.getTier());

                ResponseEntity<ResponseGetChampionRecommendationDto> response2 = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        new HttpEntity<>(requestPredictDto),
                        ResponseGetChampionRecommendationDto.class);

                responseGetChampionRecommendationDto = response2.getBody();

                System.out.println("성공");
                return ResponseEntity.status(HttpStatus.OK).body(responseGetChampionRecommendationDto);
            } catch (Exception e) {
                System.out.println("api/predict 오류 " + e.getMessage());
                responseGetChampionRecommendationDto.setErrorMessage("api/predict 오류");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGetChampionRecommendationDto);
            }
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


