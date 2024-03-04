package org.example.garencrawling.mostchampion.service;

import lombok.RequiredArgsConstructor;

import org.example.garencrawling.global.SpringAsyncConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MostChampionServiceImpl implements MostChampionService {

    private final AsyncService asyncService;

    @Override
    public ResponseEntity<?> mostChampionCrawling(Long startPlayerId, Long endPlayerId) {

//        System.out.println("startPlayerId = " + startPlayerId + " endPlayerId = " + endPlayerId + " Request 시작");
        assignThreadTask(startPlayerId, endPlayerId, SpringAsyncConfig.corePoolSize);
//        System.out.println("startPlayerId = " + startPlayerId + " endPlayerId = " + endPlayerId + " Request 종료");

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    public void assignThreadTask(long startPlayerId, long endPlayerId, int threadSize) {

        long totalPlayers = endPlayerId - startPlayerId + 1;
        long share = totalPlayers / threadSize;
        long remainder = totalPlayers % threadSize;

        long[] batchSizes = new long[threadSize];
        for (int i = 0; i < batchSizes.length; i++) {
            batchSizes[i] += share;
        }

        for (int i = 0; i < batchSizes.length; i++) {
            if (remainder == 0)
                break;
            batchSizes[i]++;
            remainder--;
        }

        long currentStartPlayerId = startPlayerId;

        for (int i = 0; i < batchSizes.length; i++) {

            if (batchSizes[i] == 0)
                break;

            long currentEndPlayerId = currentStartPlayerId + batchSizes[i] - 1;

            asyncService.processPlayersInRange(currentStartPlayerId, currentEndPlayerId);

            currentStartPlayerId = currentEndPlayerId + 1;
        }
    }


}
