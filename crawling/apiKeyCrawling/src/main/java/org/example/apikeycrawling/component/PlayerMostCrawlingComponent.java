package org.example.apikeycrawling.component;

import com.google.common.collect.Lists;
import lombok.*;
import org.example.apikeycrawling.dto.MatchDto;
import org.example.apikeycrawling.entity.mongo.PlayerMatch;
import org.example.apikeycrawling.entity.mongo.PlayerMost;
import org.example.apikeycrawling.entity.mysql.PlayerInfoTest;
import org.example.apikeycrawling.global.GlobalConstants;
import org.example.apikeycrawling.repository.ApiKeyRepository;
import org.example.apikeycrawling.repository.PlayerInfoTestRepository;

import org.example.apikeycrawling.repository.PlayerMatchRepository;
import org.example.apikeycrawling.repository.PlayerMostRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PlayerMostCrawlingComponent {

    private final ApiKeyRepository apiKeyRepository;
    private final PlayerInfoTestRepository playerInfoTestRepository;
    private final PlayerMostRepository playerMostRepository;
    private final PlayerMostCrawlingAsyncService playerMostCrawlingAsyncService;
    private final PlayerMatchRepository playerMatchRepository;

    public void crawlingVersion1() throws InterruptedException {
        GlobalConstants.apiKeys = apiKeyRepository.findAll();

        StringBuilder sb;
        int pageNumber = 0;
        int pageSize = 30;
        Page<PlayerInfoTest> page;

        do {
            page = playerInfoTestRepository.findAll(PageRequest.of(pageNumber, pageSize));
            List<PlayerInfoTest> foundPagePlayerInfoTests = page.getContent();

            ArrayList<PlayerMost> playerMosts = new ArrayList<>();
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (PlayerInfoTest foundPagePlayerInfoTest : foundPagePlayerInfoTests) {
                CompletableFuture<Void> future = playerMostCrawlingAsyncService.crawlingPlayerInfoTestOnePerson(playerMosts, foundPagePlayerInfoTest);
                futures.add(future);
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            playerMostRepository.saveAll(playerMosts);

            pageNumber++; // 다음 페이지로

            sb = new StringBuilder();
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                    .append(" page.getTotalPages() = " + page.getTotalPages())
                    .append(" 중 pageNumber = ").append(pageNumber).append(" 완료");
            System.out.println(sb);
        } while (pageNumber < page.getTotalPages()); // 전체 페이지 수에 도달할 때까지 반복

    }

    public void crawlingVersion2() {

        StringBuilder sb;
        int pageNumber = 0;
        int pageSize = 1000;
        Page<PlayerMatch> page;

        HashMap<String, HashMap<String, WinLose>> newPlayers = new HashMap<>();

        do {
            page = playerMatchRepository.findAll(PageRequest.of(pageNumber, pageSize));
            List<PlayerMatch> foundPagePlayerMatchs = page.getContent();

            for (PlayerMatch foundPagePlayerMatch : foundPagePlayerMatchs) {
                for (PlayerMatch.ParticipantDto participant : foundPagePlayerMatch.getInfo().getParticipants()) {

                    String curName = participant.getRiotIdGameName() + "-" + participant.getRiotIdTagline();

                    if (!newPlayers.containsKey(curName))
                        newPlayers.put(curName, new HashMap<>());

                    if (!newPlayers.get(curName).containsKey(participant.getChampionName()))
                        newPlayers.get(curName).put(participant.getChampionName(), new WinLose());

                    if (participant.getWin()) {
                        newPlayers.get(curName).get(participant.getChampionName()).setWin(
                                newPlayers.get(curName).get(participant.getChampionName()).getWin() + 1
                        );
                    } else {
                        newPlayers.get(curName).get(participant.getChampionName()).setLose(
                                newPlayers.get(curName).get(participant.getChampionName()).getLose() + 1
                        );
                    }
                }
            }

            pageNumber++; // 다음 페이지로
            sb = new StringBuilder();
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                    .append(" page.getTotalPages() = " + page.getTotalPages())
                    .append(" 중 pageNumber = ").append(pageNumber).append(" 완료");
            System.out.println(sb);

        } while (pageNumber < page.getTotalPages()); // 전체 페이지 수에 도달할 때까지 반복
//        } while (pageNumber < 5); // 전체 페이지 수에 도달할 때까지 반복


        List<PlayerInfoTest> findedPlayerInfoTests = playerInfoTestRepository.findAll();
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" findedPlayerInfoTests 완료");
        System.out.println(sb);

        List<PlayerMost> newPlayerMosts = new ArrayList<>();
        for (PlayerInfoTest findedPlayerInfoTest : findedPlayerInfoTests) {

            String curName = findedPlayerInfoTest.getSummonerName() + "-" + findedPlayerInfoTest.getTagLine();

            if (newPlayers.containsKey(curName))
                calculate(newPlayers.get(curName), findedPlayerInfoTest, newPlayerMosts);
        }
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" newPlayerMosts 완료");
        System.out.println(sb);

        // 한번에 저장
        playerMostRepository.saveAll(newPlayerMosts);


        int chunkSize = 1000; // Define your chunk size
        List<List<PlayerMost>> chunks = Lists.partition(newPlayerMosts, chunkSize);

        int totalChunks = chunks.size();
        for (int i = 0; i < totalChunks; i++) {

            playerMostRepository.saveAll(chunks.get(i));

            int progress = (int) ((i + 1) * 100.0 / totalChunks);

            sb = new StringBuilder();
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                    .append(" Progress: " + progress + "%");
            System.out.println(sb);

        }

    }

    public void calculate(HashMap<String, WinLose> newPlayer, PlayerInfoTest findedPlayerInfoTest, List<PlayerMost> newPlayerMosts) {

        PlayerMost newPlayerMost = new PlayerMost();

        // playerId
        newPlayerMost.setPlayerId(findedPlayerInfoTest.getPlayerId());

        // mostDatas
        ArrayList<PlayerMost.MostData> newMostDatas = new ArrayList<>();

        for (String championName : newPlayer.keySet()) {
            PlayerMost.MostData newMostData = new PlayerMost.MostData();

            // champion
            newMostData.setChampion(championName);

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
            newMostData.setGame(game);

            // add
            newMostDatas.add(newMostData);
        }

        // mostDatas 판수로 정렬
        Collections.sort(newMostDatas, new Comparator<PlayerMost.MostData>() {
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
        for (int i = 0; i < newMostDatas.size(); i++)
            newMostDatas.get(i).setMostSeq(String.valueOf(i + 1));

        // mostDatas 완성
        newPlayerMost.setMostDatas(newMostDatas);

        // 추가
        newPlayerMosts.add(newPlayerMost);
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
