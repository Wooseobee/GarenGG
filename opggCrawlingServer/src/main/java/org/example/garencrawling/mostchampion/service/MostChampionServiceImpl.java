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

    @Override
    public ResponseEntity<?> mostChampionCrawling() {

        List<PlayerInfo> savedPlayerInfos = playerInfoRepository.findAll();
        for (PlayerInfo savedPlayerInfo : savedPlayerInfos) {
            String userNickname = savedPlayerInfo.getSummonerName() + "-" + savedPlayerInfo.getTagLine();
            savedPlayerInfo.setUserNickname(userNickname);
        }

        assignThreadTask(savedPlayerInfos, 1);

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    public void assignThreadTask(List<PlayerInfo> savedPlayerInfos, int threadSize) {

        int startPlayerId = 1;
        int endPlayerId = savedPlayerInfos.size();

        int totalPlayers = endPlayerId - startPlayerId + 1;
        int share = totalPlayers / threadSize;
        int remainder = totalPlayers % threadSize;

        int[] batchSizes = new int[threadSize];
        for (int i = 0; i < batchSizes.length; i++) {
            batchSizes[i] += share;
        }

        for (int i = 0; i < batchSizes.length; i++) {
            if (remainder == 0)
                break;
            batchSizes[i]++;
            remainder--;
        }

        int currentStartPlayerId = startPlayerId;
        for (int i = 0; i < batchSizes.length; i++) {

            if (batchSizes[i] == 0)
                break;

            int currentEndPlayerId = currentStartPlayerId + batchSizes[i] - 1;

            asyncService.processPlayersInRange(currentStartPlayerId, currentEndPlayerId, savedPlayerInfos);

            currentStartPlayerId = currentEndPlayerId + 1;
        }
    }
}