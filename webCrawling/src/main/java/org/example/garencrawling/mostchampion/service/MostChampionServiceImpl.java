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
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MostChampionServiceImpl implements MostChampionService {

    private final PlayerMatchRepository playerMatchRepository;
    private final PlayerInfoRepository playerInfoRepository;
    private final PlayerMostRepository playerMostRepository;

    public Page<PlayerMatch> getPlayerMatches(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return playerMatchRepository.findAll(pageable);
    }

    @Override
    public void calculateMostChampion() {

        // 내가 읽을 players들
        HashMap<String, HashMap<String, WinLose>> players = new HashMap<>();

        // 0 페이지 100개 찾아오기
        List<PlayerMatch> findedSubPlayerMatches = getPlayerMatches(0, 100).getContent();

        // 100개 만큼 돌면서
        for (PlayerMatch findedPlayerMatch : findedSubPlayerMatches) {
            // 참가자들을 하나씩 확인
            for (Participant findedParticipant : findedPlayerMatch.getInfo().getParticipants()) {

                HashMap<String, WinLose> championsData;
                championsData = players.getOrDefault(findedParticipant.getPuuid(), new HashMap<>());

                WinLose championWinLose;
                championWinLose = championsData.getOrDefault(findedParticipant.getChampionName(), new WinLose());

                if (findedParticipant.getWin())
                    championWinLose.setWin(championWinLose.getWin() + 1);
                else
                    championWinLose.setLose(championWinLose.getLose() + 1);

                championsData.put(findedParticipant.getChampionName(), championWinLose);
                players.put(findedParticipant.getPuuid(), championsData);
            }
        }

        // 전부 돌아서 players를 완성시켜

        List<PlayerInfo> findedPlayerInfos = playerInfoRepository.findAll();

        for (PlayerInfo playerInfo : findedPlayerInfos) {
            if (players.containsKey(playerInfo.getPuuid())) {

                PlayerMost playerMost = new PlayerMost();
                playerMost.setPlayerId(playerInfo.getPlayerId());

                String tier = playerInfo.getTier();
                tier = tier.toLowerCase();
                tier = Character.toUpperCase(tier.charAt(0)) + tier.substring(1);
                playerMost.setTier(tier);
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
                ArrayList<MostData> mostDatas = new ArrayList<>();
                HashMap<String, WinLose> championsData = players.get(playerInfo.getPuuid());
                for (String championName : championsData.keySet()) {
                    MostData mostData = new MostData();

                    mostData.setChampion(championName);

                    int win = championsData.get(championName).getWin();
                    int lose = championsData.get(championName).getLose();
                    int percentage = win * 100 / (win + lose);
                    String game = "";
                    if (win != 0)
                        game = game + win + "W";
                    if (lose != 0)
                        game = game + lose + "L";
                    game = game + percentage + "%";
                    mostData.setGame(game);

                    mostDatas.add(mostData);
                }

                Collections.sort(mostDatas, new Comparator<MostData>() {
                    @Override
                    public int compare(MostData o1, MostData o2) {
                        Pattern pattern = Pattern.compile("(\\d+)([WLP])");

                        int o1sum = 0;
                        Matcher matcher = pattern.matcher(o1.getGame());
                        while (matcher.find()) {
                            String number = matcher.group(1);
                            String letter = matcher.group(2);

                            switch (letter) {
                                case "W", "L":
                                    o1sum += Integer.parseInt(number);
                                    break;
                            }
                        }

                        int o2sum = 0;
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

                for (int i = 0; i < mostDatas.size(); i++) {
                    mostDatas.get(i).setMostSeq(String.valueOf(i + 1));
                }

                playerMost.setMostDatas(mostDatas);


                playerMostRepository.save(playerMost);

            }
        }

    }
}