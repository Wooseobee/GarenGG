package org.example.garencrawling.mostchampion.service;

import lombok.RequiredArgsConstructor;
import org.example.garencrawling.mostchampion.domain.readmongo.Participant;
import org.example.garencrawling.mostchampion.domain.readmongo.PlayerMatch;
import org.example.garencrawling.mostchampion.domain.readmysql.PlayerInfo;
import org.example.garencrawling.mostchampion.domain.tmp.WinLose;
import org.example.garencrawling.mostchampion.domain.writemongo.MostData;
import org.example.garencrawling.mostchampion.domain.writemongo.PlayerMost;
import org.example.garencrawling.mostchampion.repository.PlayerInfoRepository;
import org.example.garencrawling.mostchampion.repository.PlayerMatchRepository;
import org.example.garencrawling.mostchampion.repository.PlayerMostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

@Service
@RequiredArgsConstructor
public class MostChampionServiceImpl implements MostChampionService {

    private final PlayerMatchRepository playerMatchRepository;
    private final PlayerInfoRepository playerInfoRepository;
    private final PlayerMostRepository playerMostRepository;

    @Override
    public void calculateMostChampion() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap<String, HashMap<String, WinLose>> players = new HashMap<>();
        int page = 0;
        int size = 1000;
        Page<PlayerMatch> pageResponse;

        do {
            PageRequest pageRequest = PageRequest.of(page, size);
            pageResponse = playerMatchRepository.findAll(pageRequest);
            List<PlayerMatch> playerMatches = pageResponse.getContent();

            // 현재 페이지의 데이터 처리
            for (PlayerMatch playerMatch : playerMatches) {
                // 참가자들을 하나씩 확인
                for (Participant participant : playerMatch.getInfo().getParticipants()) {

                    if (!players.containsKey(participant.getPuuid()))
                        players.put(participant.getPuuid(), new HashMap<>());

                    if (!players.get(participant.getPuuid()).containsKey(participant.getChampionName()))
                        players.get(participant.getPuuid()).put(participant.getChampionName(), new WinLose());

                    if (participant.getWin()) {
                        players.get(participant.getPuuid()).get(participant.getChampionName()).setWin(
                                players.get(participant.getPuuid()).get(participant.getChampionName()).getWin() + 1
                        );
                    } else {
                        players.get(participant.getPuuid()).get(participant.getChampionName()).setLose(
                                players.get(participant.getPuuid()).get(participant.getChampionName()).getLose() + 1
                        );
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("현재 시간: ").append(formatter.format(new Date()))
                    .append(" 전체 페이지 : ").append(pageResponse.getTotalPages()).append(" 중 ").append((page + 1)).append(" 완료");
            System.out.println(sb);

            page++; // 다음 페이지로
        } while (page < pageResponse.getTotalPages()); // 전체 페이지 수보다 작을 때까지 반복
//        } while (page < 3); // 전체 페이지 수보다 작을 때까지 반복

        // 전부 돌아서 players를 완성시킨 후
        List<PlayerInfo> findedPlayerInfos = playerInfoRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date()))
                .append(" playerInfoRepository.findAll(); 완료");
        System.out.println(sb);

        List<PlayerMost> playerMosts = new ArrayList<>();
        for (PlayerInfo playerInfo : findedPlayerInfos) {
            if (players.containsKey(playerInfo.getPuuid())) {

                PlayerMost playerMost = new PlayerMost();

                // playerId
                playerMost.setPlayerId(playerInfo.getPlayerId());

                // tier
                String tier = playerInfo.getTier();
                tier = tier.toLowerCase();
                tier = Character.toUpperCase(tier.charAt(0)) + tier.substring(1);
                playerMost.setTier(tier);

                // rankNum
                if (!(tier.equals("Master") || tier.equals("Grandmaster") || tier.equals("Challenger"))) {
                    String rankNum = playerInfo.getRankNum();
                    if (rankNum.equals("I"))
                        playerMost.setRankNum("1");
                    else if (rankNum.equals("II"))
                        playerMost.setRankNum("2");
                    else if (rankNum.equals("III"))
                        playerMost.setRankNum("3");
                    else if (rankNum.equals("IV"))
                        playerMost.setRankNum("4");
                }

                // mostDatas
                ArrayList<MostData> mostDatas = new ArrayList<>();
                for (String championName : players.get(playerInfo.getPuuid()).keySet()) {
                    MostData mostData = new MostData();

                    // champion
                    mostData.setChampion(championName);

                    // game
                    int win = players.get(playerInfo.getPuuid()).get(championName).getWin();
                    int lose = players.get(playerInfo.getPuuid()).get(championName).getLose();
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

                // 추가
                playerMosts.add(playerMost);
            }
        }

        // 한번에 저장
        playerMostRepository.saveAll(playerMosts);
    }
}