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
        readPlayerMatch(oldPlayers, startPageNumber, endPageNumber);
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" readPlayerMatch 완료");
        System.out.println(sb);

        ////////////////////////////// newPlayerMosts ////////////////////////////////
        List<PlayerMost> newPlayerMosts = new ArrayList<>();
        for (String curName : oldPlayers.keySet()) {
            if (!hashMapNamePlayerInfoTest.containsKey(curName)) {
                continue;
            }
            calculate(oldPlayers.get(curName), newPlayerMosts, hashMapNamePlayerInfoTest, curName);
        }
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" newPlayerMosts 완료");
        System.out.println(sb);

        ////////////////////////////// saveAll ////////////////////////////////
        List<PlayerMost> temp = new ArrayList<>();

        int batchSize = 1000;
        int totalSize = newPlayerMosts.size();
        for (int i = 0; i < totalSize; i++) {
            temp.add(newPlayerMosts.get(i));

            if ((i + 1) % batchSize == 0 || i == totalSize - 1) {
                playerMostRepository.saveAll(temp);
                temp.clear();

                // 처리된 데이터의 비율(퍼센트) 계산
                double percentCompleted = ((double) (i + 1) / totalSize) * 100;

                // 현재 시간과 처리된 데이터의 비율(퍼센트)를 출력
                String progressMessage = String.format("현재 시간: %s, 완료: %.2f%%",
                        GlobalConstants.formatter.format(new Date()), percentCompleted);

                System.out.println(progressMessage);
            }
        }
    }

    public void readPlayerMost(HashMap<String, HashMap<String, WinLose>> oldPlayers,
                               HashMap<Integer, PlayerInfoTest> hashMapPlayerIdPlayerInfoTest) {
        StringBuilder sb;
        int pageNumber = 0;
        int pageSize = 1000;
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

    public void readPlayerMatch(HashMap<String, HashMap<String, WinLose>> oldPlayers, int startPageNumber, int endPageNumber) {

        StringBuilder sb;
        int pageNumber = startPageNumber;
        int pageSize = 1000;
        Page<PlayerMatch> page;

        do {
            page = playerMatchRepository.findAll(PageRequest.of(pageNumber, pageSize));
            List<PlayerMatch> curPlayerMatchs = page.getContent();

            // 한 경기 씩
            for (PlayerMatch curPlayerMatch : curPlayerMatchs) {

                // 한명씩
                for (PlayerMatch.ParticipantDto participant : curPlayerMatch.getInfo().getParticipants()) {

                    String curName = participant.getRiotIdGameName() + "-" + participant.getRiotIdTagline();

                    if (!oldPlayers.containsKey(curName))
                        oldPlayers.put(curName, new HashMap<>());

                    if (!oldPlayers.get(curName).containsKey(participant.getChampionName()))
                        oldPlayers.get(curName).put(participant.getChampionName(), new WinLose());

                    if (participant.getWin()) {
                        oldPlayers.get(curName).get(participant.getChampionName()).setWin(
                                oldPlayers.get(curName).get(participant.getChampionName()).getWin() + 1
                        );
                    } else {
                        oldPlayers.get(curName).get(participant.getChampionName()).setLose(
                                oldPlayers.get(curName).get(participant.getChampionName()).getLose() + 1
                        );
                    }
                }
            }

            pageNumber++;

            sb = new StringBuilder();
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                    .append(" startPageNumber = ").append(startPageNumber)
                    .append(" endPageNumber = ").append(endPageNumber)
                    .append(" 중 pageNumber = ").append(pageNumber).append(" 완료");
            System.out.println(sb);

        } while (pageNumber < endPageNumber);


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
