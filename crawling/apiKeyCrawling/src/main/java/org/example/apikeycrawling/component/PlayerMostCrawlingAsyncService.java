package org.example.apikeycrawling.component;

import lombok.*;
import org.example.apikeycrawling.dto.MatchDto;
import org.example.apikeycrawling.entity.mongo.PlayerMost;
import org.example.apikeycrawling.entity.mysql.PlayerInfoTest;
import org.example.apikeycrawling.global.GlobalConstants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PlayerMostCrawlingAsyncService {


    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Void> crawlingPlayerInfoTestOnePerson(ArrayList<PlayerMost> playerMosts, PlayerInfoTest foundPagePlayerInfoTest) throws InterruptedException {

        StringBuilder sb;
        int apiKeysIndex = Integer.parseInt(foundPagePlayerInfoTest.getApiKeyId()) - 1;
        String url;
        List<String> receivedMatchIds = null;

        while (true) {
            try {
                url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + foundPagePlayerInfoTest.getPuuid() + "/ids?startTime=1704844800&queue=420&count=100&api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                receivedMatchIds = GlobalConstants.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                }).getBody();
                break;
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    Thread.sleep(1000);
                } else {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }

        if (receivedMatchIds == null) {
            sb = new StringBuilder();
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                    .append(" playerId = " + foundPagePlayerInfoTest.getPlayerId())
                    .append(" 실패");
            System.out.println(sb);
            return CompletableFuture.completedFuture(null);
        }

        List<MatchDto> receivedMatchDtos = new ArrayList<>();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (String receivedMatchId : receivedMatchIds) {
            CompletableFuture<Void> future = crawlingMatchDto(receivedMatchDtos, receivedMatchId, foundPagePlayerInfoTest, apiKeysIndex);
            apiKeysIndex = (apiKeysIndex + 1) % GlobalConstants.apiKeys.size();
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        HashMap<String, WinLose> player = new HashMap<>();

        int findCount = 0;
        for (MatchDto curReceivedMatchDto : receivedMatchDtos) {
            for (MatchDto.ParticipantDto curParticipantDto : curReceivedMatchDto.getInfo().getParticipants()) {

                if (curParticipantDto.getRiotIdGameName().equals(foundPagePlayerInfoTest.getSummonerName())
                        && curParticipantDto.getRiotIdTagline().equals(foundPagePlayerInfoTest.getTagLine())) {

                    findCount++;

                    if (!player.containsKey(curParticipantDto.getChampionName()))
                        player.put(curParticipantDto.getChampionName(), new WinLose());

                    if (curParticipantDto.getWin())
                        player.get(curParticipantDto.getChampionName()).setWin(player.get(curParticipantDto.getChampionName()).getWin() + 1);
                    else
                        player.get(curParticipantDto.getChampionName()).setLose(player.get(curParticipantDto.getChampionName()).getLose() + 1);

                }
            }
        }

        // playerMost 완성하기
        PlayerMost newPlayerMost = new PlayerMost();

        // playerId
        newPlayerMost.setPlayerId(foundPagePlayerInfoTest.getPlayerId());

        // mostDatas
        ArrayList<PlayerMost.MostData> mostDatas = new ArrayList<>();
        for (String championName : player.keySet()) {
            PlayerMost.MostData mostData = new PlayerMost.MostData();

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
        Collections.sort(mostDatas, new Comparator<PlayerMost.MostData>() {
            @Override
            public int compare(PlayerMost.MostData o1, PlayerMost.MostData o2) {
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
        newPlayerMost.setMostDatas(mostDatas);

        playerMosts.add(newPlayerMost);

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" playerId = " + foundPagePlayerInfoTest.getPlayerId())
                .append(" receivedMatchIds.size() = " + receivedMatchIds.size())
                .append(" receivedMatchDtos.size() = " + receivedMatchDtos.size())
                .append(" findCount = " + findCount);
        System.out.println(sb);

        return CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Void> crawlingMatchDto(List<MatchDto> receivedMatchDtos, String receivedMatchId, PlayerInfoTest curFindedPlyerInfoTest, int apiKeysIndex) {

        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/{match}?api_key={apiKey}";
        while (true) {
            try {
                MatchDto receivedMatchDto = GlobalConstants.restTemplate.getForObject(url, MatchDto.class, receivedMatchId, GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey());
                apiKeysIndex = (apiKeysIndex + 1) % GlobalConstants.apiKeys.size();
                receivedMatchDtos.add(receivedMatchDto);
                break;
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    System.out.println("playerId = " + curFindedPlyerInfoTest.getPlayerId() + " receivedMatchDto 받기 " + e.getStatusText());
                } else {
                    System.out.println("playerId = " + curFindedPlyerInfoTest.getPlayerId() + " receivedMatchDto 받기 " + e.getStatusText());
                    break;
                }
            } catch (Exception e) {
                System.out.println("playerId = " + curFindedPlyerInfoTest.getPlayerId() + " receivedMatchDto 받기 " + e.getMessage());
                break;
            }
        }

        return CompletableFuture.completedFuture(null);
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
