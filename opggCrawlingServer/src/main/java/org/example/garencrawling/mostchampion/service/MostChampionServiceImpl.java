package org.example.garencrawling.mostchampion.service;

import lombok.RequiredArgsConstructor;

import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.example.garencrawling.mostchampion.repository.PlayerInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor

public class MostChampionServiceImpl implements MostChampionService {

    private final AsyncService asyncService;
    private final PlayerInfoRepository playerInfoRepository;

    public static int threadSize = 5;

    @Override
    public void mostChampionCrawling(int startPlayerId, int endPlayerId) throws InterruptedException {

        List<PlayerInfo> findedPlayerInfos = playerInfoRepository.findByPlayerIdBetween(startPlayerId, endPlayerId);

        for (PlayerInfo findedPlayerInfo : findedPlayerInfos) {
            String userNickname = findedPlayerInfo.getSummonerName() + "-" + findedPlayerInfo.getTagLine();
            findedPlayerInfo.setUserNickname(userNickname);
        }

        ArrayList<ArrayList<PlayerInfo>> subFindedPlayerInfos = new ArrayList<>();
        for (int i = 0; i < threadSize; i++) {
            subFindedPlayerInfos.add(new ArrayList<>());
        }

        for (int i = 0; i < findedPlayerInfos.size(); i++) {
            subFindedPlayerInfos.get(i % threadSize).add(findedPlayerInfos.get(i));
        }

        for (int i = 0; i < threadSize; i++) {
            asyncService.processPlayersInRange(subFindedPlayerInfos.get(i), i + 1);
        }
    }
}