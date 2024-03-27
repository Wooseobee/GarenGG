package gg.garen.back.championRecommendation.service;

import gg.garen.back.championRecommendation.dto.response.ResponseGetChampionRecommendationDto;
import gg.garen.back.common.domain.mongo.MatchInfo;
import gg.garen.back.common.domain.mongo.MostData;
import gg.garen.back.common.domain.mongo.Participant;
import gg.garen.back.common.domain.mongo.PlayerMost;
import gg.garen.back.common.domain.mysql.ApiKey;
import gg.garen.back.common.domain.mysql.PlayerInfo;
import gg.garen.back.common.repository.PlayerInfoRepository;
import gg.garen.back.common.repository.PlayerMostRepository;
import gg.garen.back.common.service.ApiKeyUtils;
import lombok.*;
import org.springframework.core.ParameterizedTypeReference;
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
    private final ApiKeyUtils apiKeyUtils;

    @Override
    public ResponseEntity<?> getChampionRecommendation(String summonerName, String tagLine) {

        ResponseGetChampionRecommendationDto responseGetChampionRecommendationDto = new ResponseGetChampionRecommendationDto();

        PlayerInfo findedPlayerInfo;
        ApiKey apiKey;
        String url;
        RestTemplate restTemplate = new RestTemplate();
        AccountDto receivedAccountDto;
        ResponseEntity<List<String>> response;
        List<String> receivedMatchIds;
        List<MatchInfo> receivedMatchInfos = null;
        MatchInfo receivedMatchInfo;
        HashMap<String, WinLose> player;

        // MySQL에서 summonerName과 tagLine로 찾아
        findedPlayerInfo = playerInfoRepository.findBySummonerNameAndTagLine(summonerName, tagLine);
        if (findedPlayerInfo == null) {

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

            PlayerInfo newPlayerInfo = new PlayerInfo();
            newPlayerInfo.setApiKeyId(apiKey.getId());
            newPlayerInfo.setPuuid(receivedAccountDto.getPuuid());
            newPlayerInfo.setSummonerName(receivedAccountDto.getGameName());
            newPlayerInfo.setTagLine(receivedAccountDto.getTagLine());
            findedPlayerInfo = playerInfoRepository.save(newPlayerInfo);
        }
        System.out.println("findedPlayerInfo.getPlayerId() = " + findedPlayerInfo.getPlayerId());
        System.out.println("findedPlayerInfo.getApiKeyId() = " + findedPlayerInfo.getApiKeyId());
        System.out.println("findedPlayerInfo.getPuuid() = " + findedPlayerInfo.getPuuid());
        System.out.println("findedPlayerInfo.getSummonerName() = " + findedPlayerInfo.getSummonerName());
        System.out.println("findedPlayerInfo.getTagLine() = " + findedPlayerInfo.getTagLine());
        System.out.println("findedPlayerInfo 성공");

        // MongoDB에서 player_id로 player_most 찾기
        PlayerMost findedPlayerMost = playerMostRepository.findById(findedPlayerInfo.getPlayerId()).orElse(null);
        if (findedPlayerMost == null) {

            // matchIds 받기
            try {
                apiKey = apiKeyUtils.getSpecificApiKey(findedPlayerInfo.getApiKeyId());
                url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + findedPlayerInfo.getPuuid() + "/ids?queue=420&count=98&api_key=" + apiKey.getApiKey();
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
                        if (participant.getSummonerName().equals(findedPlayerInfo.getSummonerName())) {
                        } else {
                            continue;
                        }
                    } else {
                        if (participant.getRiotIdGameName().equals(findedPlayerInfo.getSummonerName())
                                && participant.getRiotIdTagline().equals(findedPlayerInfo.getTagLine())) {
                        } else {
                            continue;
                        }
                    }

                    findCount++;

                    if (!player.containsKey(participant.getChampionName()))
                        player.put(participant.getChampionName(), new WinLose());
                    if (participant.getWin())
                        player.get(participant.getChampionName()).setWin(player.get(participant.getChampionName()).getWin() + 1);
                    else
                        player.get(participant.getChampionName()).setLose(player.get(participant.getChampionName()).getLose() + 1);

                }
            }
            System.out.println("findCount = " + findCount);

            // playerMost 완성하기
            PlayerMost playerMost = new PlayerMost();

            // playerId
            playerMost.setPlayerId(findedPlayerInfo.getPlayerId());

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
            playerMost.setMostDatas(mostDatas);

            findedPlayerMost = playerMostRepository.save(playerMost);
        }

        System.out.println("findedPlayerMost.getPlayerId() = " + findedPlayerMost.getPlayerId());
        System.out.println("findedPlayerMost.getTier() = " + findedPlayerMost.getTier());
        System.out.println("findedPlayerMost.getRankNum() = " + findedPlayerMost.getRankNum());
        System.out.println("findedPlayerMost.getMostDatas().size() = " + findedPlayerMost.getMostDatas().size());
        for (MostData mostData : findedPlayerMost.getMostDatas())
            System.out.println(mostData.getMostSeq() + " " + mostData.getChampion() + " " + mostData.getGame());
        System.out.println("findedPlayerMost 성공");

        // FAST API 호출

        // 테스팅
        responseGetChampionRecommendationDto.setChampionRecommendationDatas(new ArrayList<>());
        responseGetChampionRecommendationDto.getChampionRecommendationDatas().add(new ResponseGetChampionRecommendationDto.ChampionRecommendationData("Annie", "애니", 0, (double) 0));
        responseGetChampionRecommendationDto.getChampionRecommendationDatas().add(new ResponseGetChampionRecommendationDto.ChampionRecommendationData("Olaf", "올라프", 0, (double) 0));
        responseGetChampionRecommendationDto.getChampionRecommendationDatas().add(new ResponseGetChampionRecommendationDto.ChampionRecommendationData("Galio", "갈리오", 0, (double) 0));

        return ResponseEntity.status(HttpStatus.OK).body(responseGetChampionRecommendationDto);
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


