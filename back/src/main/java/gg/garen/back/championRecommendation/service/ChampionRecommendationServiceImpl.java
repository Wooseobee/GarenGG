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
    public ResponseGetChampionRecommendationDto getChampionRecommendation(String summonerName, String tagLine) {
        PlayerInfo findedPlayerInfo;
        ApiKey apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String url;
        AccountDto receivedAccountDto;
        ResponseEntity<List<String>> response;
        List<String> receivedMatchIds;
        MatchInfo receivedMatchInfo;
        List<MatchInfo> receivedMatchInfos;
        HashMap<String, WinLose> player;

        findedPlayerInfo = playerInfoRepository.findBySummonerNameAndTagLine(summonerName, tagLine);
        if (findedPlayerInfo == null) {
            apiKey = apiKeyUtils.getOneApiKey();

            // 롤 API로 puuid 얻기
            url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{summonerName}/{tagLine}?api_key={apiKey}";
            while (true) {
                try {
                    receivedAccountDto = restTemplate.getForObject(url, AccountDto.class, summonerName, tagLine, apiKey.getApiKey());
                    break;
                } catch (HttpClientErrorException e) {
                    // 키 폭발
                    if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                        System.out.println(e.getStatusText());
                        apiKey = apiKeyUtils.getOneApiKey();
                    }
                    // 없는 유저
                    else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        System.out.println(e.getStatusText());
                        return null;
                    }
                    // 기타 다른 HTTP 에러
                    else {
                        System.out.println("HTTP 오류: " + e.getStatusCode());
                        System.out.println("오류 메시지: " + e.getStatusText());
                        return null;
                    }
                }
            }

            PlayerInfo newPlayerInfo = new PlayerInfo();
            newPlayerInfo.setPuuid(receivedAccountDto.getPuuid());
            newPlayerInfo.setSummonerName(receivedAccountDto.getGameName());
            newPlayerInfo.setTagLine(receivedAccountDto.getTagLine());
            newPlayerInfo.setApiKeyId(apiKey.getId());
            findedPlayerInfo = playerInfoRepository.save(newPlayerInfo);
        }
        System.out.println("findedPlayerInfo.getPlayerId() = " + findedPlayerInfo.getPlayerId());
        System.out.println("findedPlayerInfo.getApiKeyId() = " + findedPlayerInfo.getApiKeyId());
        System.out.println("findedPlayerInfo.getPuuid() = " + findedPlayerInfo.getPuuid());
        System.out.println("findedPlayerInfo.getSummonerName() = " + findedPlayerInfo.getSummonerName());
        System.out.println("findedPlayerInfo.getTagLine() = " + findedPlayerInfo.getTagLine());
        System.out.println("findedPlayerInfo 해결");

        PlayerMost findedPlayerMost = playerMostRepository.findById(findedPlayerInfo.getPlayerId()).orElse(null);
        if (findedPlayerMost == null) {

            apiKey = apiKeyUtils.getSpecificApiKey(findedPlayerInfo.getApiKeyId());

            // 롤 API로 매치 아이디들 얻기
            url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + findedPlayerInfo.getPuuid() + "/ids?queue=420&count=98&api_key=" + apiKey.getApiKey();
            try {
                response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<String>>() {
                        }
                );
                receivedMatchIds = response.getBody();
            } catch (HttpClientErrorException e) {
                // 키 폭발
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    System.out.println(e.getStatusText());
                    return null;
                } else {
                    System.out.println("HTTP 오류: " + e.getStatusCode());
                    System.out.println("오류 메시지: " + e.getStatusText());
                    return null;
                }
            }

//            System.out.println("receivedMatchIds.size() = " + receivedMatchIds.size());
//            for (int i = 0; i < receivedMatchIds.size(); i++)
//                System.out.println("i = " + i + " receivedMatchId = " + receivedMatchIds.get(i));

            while (true) {
                // 롤 API로 매치들 상세 정보 얻기
                receivedMatchInfos = new ArrayList<>();
                url = "https://asia.api.riotgames.com/lol/match/v5/matches/{match}?api_key={apiKey}";
                try {
                    for (String receiveMatchId : receivedMatchIds) {
                        receivedMatchInfo = restTemplate.getForObject(url, MatchInfo.class, receiveMatchId, apiKeyUtils.getOneApiKey().getApiKey());
                        receivedMatchInfos.add(receivedMatchInfo);
//                        System.out.println(receiveMatchId);
                    }
                    break;
                } catch (HttpClientErrorException e) {
                    // 키 폭발
                    if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                        System.out.println(e.getStatusText());
                    } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        System.out.println(e.getStatusText());
                        break;
                    } else {
                        System.out.println("HTTP 오류: " + e.getStatusCode());
                        System.out.println("오류 메시지: " + e.getStatusText());
                        return null;
                    }
                }
            }

            // 상세 정보로 모스트 챔피언 정리하기
            player = new HashMap<>();
            function1(player, receivedMatchInfos, findedPlayerInfo);
            findedPlayerMost = function2(player, findedPlayerInfo);

        }
        System.out.println("findedPlayerMost.getPlayerId() = " + findedPlayerMost.getPlayerId());
        System.out.println("findedPlayerMost.getTier() = " + findedPlayerMost.getTier());
        System.out.println("findedPlayerMost.getRankNum() = " + findedPlayerMost.getRankNum());
        for (MostData mostData : findedPlayerMost.getMostDatas())
            System.out.println(mostData.getMostSeq() + " " + mostData.getChampion() + " " + mostData.getGame());
        System.out.println("findedPlayerMost 해결");

        // FAST API 호출


        return null;
    }

    public void function1(HashMap<String, WinLose> player, List<MatchInfo> receivedMatchInfos, PlayerInfo findedPlayerInfo) {

        // 현재 페이지의 데이터 처리
        for (MatchInfo playerMatch : receivedMatchInfos) {
            // 참가자들을 하나씩 확인
            for (Participant participant : playerMatch.getInfo().getParticipants()) {

                if (participant.getSummonerName().equals(findedPlayerInfo.getSummonerName())) {

                    if (!player.containsKey(participant.getChampionName()))
                        player.put(participant.getChampionName(), new WinLose());

                    if (participant.getWin()) {
                        player.get(participant.getChampionName()).setWin(
                                player.get(participant.getChampionName()).getWin() + 1
                        );
                    } else {
                        player.get(participant.getChampionName()).setLose(
                                player.get(participant.getChampionName()).getLose() + 1
                        );
                    }

                }
            }
        }
    }

    public PlayerMost function2(HashMap<String, WinLose> player, PlayerInfo playerInfo) {

        PlayerMost playerMost = new PlayerMost();

        // playerId
        playerMost.setPlayerId(playerInfo.getPlayerId());

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

        playerMostRepository.save(playerMost);

        return playerMost;
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


