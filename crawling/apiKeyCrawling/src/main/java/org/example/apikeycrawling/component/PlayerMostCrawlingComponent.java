package org.example.apikeycrawling.component;

import lombok.*;
import org.example.apikeycrawling.entity.mongo.PlayerMatch;
import org.example.apikeycrawling.entity.mongo.PlayerMost;
import org.example.apikeycrawling.entity.mysql.PlayerInfoTest;
import org.example.apikeycrawling.global.GlobalConstants;
import org.example.apikeycrawling.repository.ApiKeyRepository;
import org.example.apikeycrawling.repository.PlayerInfoTestRepository;

import org.example.apikeycrawling.repository.PlayerMatchRepository;
import org.example.apikeycrawling.repository.PlayerMostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

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

            sb = new StringBuilder();
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                    .append(" page.getTotalPages() = " + page.getTotalPages())
                    .append(" 중 pageNumber = ").append(pageNumber).append(" 완료");
            System.out.println(sb);

            pageNumber++; // 다음 페이지로
        } while (pageNumber < page.getTotalPages()); // 전체 페이지 수에 도달할 때까지 반복

    }

    public void crawlingVersion2(int startPageNumber, int endPageNumber) {
        StringBuilder sb;

        ////////////////////////////// 시작 ////////////////////////////////
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" startPageNumber = ").append(startPageNumber)
                .append(" endPageNumber = ").append(endPageNumber).append(" 시작");
        System.out.println(sb);

        ////////////////////////////// foundPlayerInfoTests ////////////////////////////////
        List<PlayerInfoTest> foundPlayerInfoTests = playerInfoTestRepository.findAll();
        HashMap<Integer, PlayerInfoTest> hashMapPlayerIdPlayerInfoTest = new HashMap<>();
        HashMap<String, PlayerInfoTest> hashMapNamePlayerInfoTest = new HashMap<>();
        for (PlayerInfoTest foundPlayerInfoTest : foundPlayerInfoTests) {
            hashMapPlayerIdPlayerInfoTest.put(foundPlayerInfoTest.getPlayerId(), foundPlayerInfoTest);
            hashMapNamePlayerInfoTest.put(
                    foundPlayerInfoTest.getSummonerName() + "-" + foundPlayerInfoTest.getTagLine(), foundPlayerInfoTest);
        }
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" foundPlayerInfoTests 완료");
        System.out.println(sb);

        ////////////////////////////// readPlayerMost ////////////////////////////////
        HashMap<String, HashMap<String, WinLose>> oldPlayers = new HashMap<>();
        readPlayerMost(oldPlayers, hashMapPlayerIdPlayerInfoTest);
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" readPlayerMost 완료");
        System.out.println(sb);

        ////////////////////////////// readPlayerMatch ////////////////////////////////
        HashMap<String, Boolean> visited = new HashMap<>();
        readPlayerMatch(oldPlayers, visited, startPageNumber, endPageNumber);
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" readPlayerMatch 완료");
        System.out.println(sb);

        ////////////////////////////// newPlayerMosts ////////////////////////////////
        List<PlayerMost> newPlayerMosts = new ArrayList<>();
        for (String curName : oldPlayers.keySet()) {
            if (!hashMapNamePlayerInfoTest.containsKey(curName))
                continue;
            if (!visited.containsKey(curName))
                continue;
            calculate(oldPlayers.get(curName), newPlayerMosts, hashMapNamePlayerInfoTest, curName);
        }
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" newPlayerMosts 완료");
        System.out.println(sb);

        ////////////////////////////// saveAll ////////////////////////////////
        int totalSize = newPlayerMosts.size();
        int batchSize = totalSize / 100; // 스레드 수를 기반으로 배치 크기 결정

        int threadNumber = 1;
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int start = 0; start < totalSize; start += batchSize) {
            int end = Math.min(start + batchSize, totalSize);
            List<PlayerMost> batchList = newPlayerMosts.subList(start, end);
            CompletableFuture<Void> future = playerMostCrawlingAsyncService.save(batchList, threadNumber);
            futures.add(future);
            threadNumber++;
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" 종료");
        System.out.println(sb);
    }

    public void readPlayerMost(HashMap<String, HashMap<String, WinLose>> oldPlayers,
                               HashMap<Integer, PlayerInfoTest> hashMapPlayerIdPlayerInfoTest) {
        StringBuilder sb;
        int pageNumber = 0;
        int pageSize = 10000;
        Page<PlayerMost> page;

        do {
            page = playerMostRepository.findAll(PageRequest.of(pageNumber, pageSize));
            List<PlayerMost> curPlayerMosts = page.getContent();

            // 한명씩
            for (PlayerMost curPlayerMost : curPlayerMosts) {

                HashMap<String, WinLose> oldPlayer = new HashMap<>();

                // 모스트 하나씩
                for (PlayerMost.MostData curMostData : curPlayerMost.getMostDatas()) {

                    oldPlayer.put(curMostData.getChampion(), new WinLose());

                    Pattern pattern = Pattern.compile("(\\d+)(W|L)");
                    Matcher matcher = pattern.matcher(curMostData.getGame());

                    while (matcher.find()) {
                        if (matcher.group(2).equals("W"))
                            oldPlayer.get(curMostData.getChampion()).setWin(Integer.valueOf(matcher.group(1)));
                        else if (matcher.group(2).equals("L"))
                            oldPlayer.get(curMostData.getChampion()).setLose(Integer.valueOf(matcher.group(1)));
                    }
                }

                oldPlayers.put(hashMapPlayerIdPlayerInfoTest.get(curPlayerMost.getPlayerId()).getSummonerName()
                                + "-"
                                + hashMapPlayerIdPlayerInfoTest.get(curPlayerMost.getPlayerId()).getTagLine()
                        , oldPlayer);
            }

            pageNumber++;

            sb = new StringBuilder();
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                    .append(" page.getTotalPages() = " + page.getTotalPages())
                    .append(" 중 pageNumber = ").append(pageNumber).append(" 완료");
            System.out.println(sb);
        } while (pageNumber < page.getTotalPages());
    }

    public void readPlayerMatch(HashMap<String, HashMap<String, WinLose>> oldPlayers
            , HashMap<String, Boolean> visited, int startPageNumber, int endPageNumber) {

        int pageNumber = startPageNumber;
        int pageSize = 10000;

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        ArrayList<HashMap<String, HashMap<String, WinLose>>> newPlayersList = new ArrayList<>();
        do {
            CompletableFuture<Void> future = playerMostCrawlingAsyncService.findSave(newPlayersList, visited, pageNumber, pageSize, startPageNumber, endPageNumber);
            futures.add(future);
            pageNumber++;

        } while (pageNumber < endPageNumber);

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // newPlayersList와 oldPlayers 합치기

    }

    public void calculate(HashMap<String, WinLose> newPlayer, List<PlayerMost> newPlayerMosts, HashMap<String, PlayerInfoTest> hashMapNamePlayerInfoTest, String curName) {

        PlayerMost newPlayerMost = new PlayerMost();

        // playerId
        newPlayerMost.setPlayerId(hashMapNamePlayerInfoTest.get(curName).getPlayerId());

        // tier
        newPlayerMost.setTier(hashMapNamePlayerInfoTest.get(curName).getTier());

        // rankNum
        newPlayerMost.setRankNum(hashMapNamePlayerInfoTest.get(curName).getRankNum());

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
