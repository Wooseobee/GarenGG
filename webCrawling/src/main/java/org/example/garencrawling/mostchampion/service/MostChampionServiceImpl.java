package org.example.garencrawling.mostchampion.service;

import lombok.RequiredArgsConstructor;
import org.example.garencrawling.mostchampion.domain.*;
import org.example.garencrawling.mostchampion.repository.PlayerInfoRepository;
import org.example.garencrawling.mostchampion.repository.PlayerMatchRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MostChampionServiceImpl implements MostChampionService {

    private final PlayerMatchRepository playerMatchRepository;
    private final PlayerInfoRepository playerInfoRepository;

    @Override
    public void calculateMostChampion() {

        HashMap<String, HashMap<String, Count>> hashMap = new HashMap<>();

//        List<PlayerMatch> findedPlayerMatches = playerMatchRepository.findByMatchIdIn(Arrays.asList("KR_6977559919", "KR_6977540684", "KR_6977509877"));
//        System.out.println("playerMatchRepository.findByMatchIdIn() 완료");

        List<PlayerMatch> findedPlayerMatches = playerMatchRepository.findAll();
        System.out.println("playerMatchRepository.findAll() 완료");

        for (PlayerMatch findedPlayerMatch : findedPlayerMatches) {
            for (Participant findedParticipant : findedPlayerMatch.getInfo().getParticipants()) {

                HashMap<String, Count> subHashMap;
                if (hashMap.containsKey(findedParticipant.getPuuid())) {
                    subHashMap = hashMap.get(findedParticipant.getPuuid());
                } else {
                    subHashMap = new HashMap<>();
                }

                Count curCount;
                if (subHashMap.containsKey(findedParticipant.getChampionName())) {
                    curCount = subHashMap.get(findedParticipant.getChampionName());
                } else {
                    curCount = new Count();
                }

                if (findedParticipant.getWin()) {
                    curCount.setWin(curCount.getWin() + 1);
                } else {
                    curCount.setLose(curCount.getLose() + 1);
                }

                subHashMap.put(findedParticipant.getChampionName(), curCount);
                hashMap.put(findedParticipant.getPuuid(), subHashMap);

            }
        }
        System.out.println("findedPlayerMatches 완료");

        List<PlayerInfo> findedPlayerInfos = playerInfoRepository.findAll();
        System.out.println("playerInfoRepository.findAll() 완료");

        ArrayList<PlayerMost> newPlayerMosts = new ArrayList<>();

        System.out.println("end");
    }
}