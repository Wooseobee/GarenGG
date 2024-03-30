package org.example.apikeycrawling;

import lombok.*;
import org.example.apikeycrawling.entity.mongo.PlayerMatch;
import org.example.apikeycrawling.entity.mongo.PlayerMost;
import org.example.apikeycrawling.entity.mysql.PlayerInfoV0;
import org.example.apikeycrawling.repository.PlayerInfoV0Repository;
import org.example.apikeycrawling.repository.PlayerMatchRepository;
import org.example.apikeycrawling.repository.PlayerMostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PlayerMostCrawlingComponent {

    private final PlayerMatchRepository playerMatchRepository;
    private final PlayerInfoV0Repository playerInfoV0Repository;
    private final PlayerMostRepository playerMostRepository;

    public void function1() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        StringBuilder sb;

        HashMap<String, HashMap<String, WinLose>> players = new HashMap<>();
        int page = 0;
        int size = 1000;
        Page<PlayerMatch> pageResponse;

        do {
            PageRequest pageRequest = PageRequest.of(page, size);
            pageResponse = playerMatchRepository.findAll(pageRequest);
            List<PlayerMatch> playerMatches = pageResponse.getContent();

            // 매치 한개씩
            for (PlayerMatch playerMatch : playerMatches) {

                // 참가자 한명씩
                for (PlayerMatch.Participant participant : playerMatch.getInfo().getParticipants()) {
                    String curName = participant.getRiotIdGameName() + "-" + participant.getRiotIdTagline();

                    if (!players.containsKey(curName))
                        players.put(curName, new HashMap<>());

                    if (!players.get(curName).containsKey(participant.getChampionName()))
                        players.get(curName).put(participant.getChampionName(), new WinLose());

                    if (participant.getWin()) {
                        players.get(curName).get(participant.getChampionName()).setWin(
                                players.get(curName).get(participant.getChampionName()).getWin() + 1
                        );
                    } else {
                        players.get(curName).get(participant.getChampionName()).setLose(
                                players.get(curName).get(participant.getChampionName()).getLose() + 1
                        );
                    }
                }
            }

            sb = new StringBuilder();
            sb.append("현재 시간: ").append(formatter.format(new Date())).append(" 전체 페이지 : ").append(pageResponse.getTotalPages()).append(" 중 ").append((page + 1)).append(" 완료");
            System.out.println(sb);

            page++; // 다음 페이지로
        } while (page < pageResponse.getTotalPages()); // 전체 페이지 수보다 작을 때까지 반복
//        } while (page < 3); // 전체 페이지 수보다 작을 때까지 반복

        ///////////////////////////////////////////////////////////////////////////////

        List<PlayerInfoV0> findedPlayerInfoV0s = playerInfoV0Repository.findAll();
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date())).append(" playerInfoV0Repository.findAll(); 완료");
        System.out.println(sb);

        ///////////////////////////////////////////////////////////////////////////////

        List<PlayerMost> playerMosts = new ArrayList<>();

        for (PlayerInfoV0 curPlayerInfoV0 : findedPlayerInfoV0s) {

            String curName = curPlayerInfoV0.getSummonerName() + "-" + curPlayerInfoV0.getTagLine();

            if (players.containsKey(curName)) {

                PlayerMost playerMost = new PlayerMost();

                // playerId
                playerMost.setPlayerId(curPlayerInfoV0.getPlayerId());

                // tier
                String tier = curPlayerInfoV0.getTier();
                tier = tier.toLowerCase();
                tier = Character.toUpperCase(tier.charAt(0)) + tier.substring(1);
                playerMost.setTier(tier);

                // mostDatas
                ArrayList<PlayerMost.MostData> mostDatas = new ArrayList<>();

                for (String curChampionName : players.get(curName).keySet()) {

                    // mostData
                    PlayerMost.MostData mostData = new PlayerMost.MostData();

                    // champion
                    mostData.setChampion(curChampionName);

                    // game
                    int win = players.get(curName).get(curChampionName).getWin();
                    int lose = players.get(curName).get(curChampionName).getLose();
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
                playerMost.setMostDatas(mostDatas.subList(0,Math.min(10, mostDatas.size())));

                // 추가
                playerMosts.add(playerMost);
            }
        }
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date())).append(" List<PlayerMost> playerMosts = new ArrayList<>(); 채우기 완료");
        System.out.println(sb);

        // 한번에 저장
        playerMostRepository.saveAll(playerMosts);
        sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date())).append(" playerMostRepository.saveAll(playerMosts); 완료");
        System.out.println(sb);
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
