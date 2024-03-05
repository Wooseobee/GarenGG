package org.example.garencrawling.mostchampion.service;

import lombok.RequiredArgsConstructor;

import org.example.garencrawling.global.SpringAsyncConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MostChampionServiceImpl implements MostChampionService {

    private final AsyncService asyncService;

    public static long startTime;
    public static long endTime;
    public static int failCount;

    @Override
    public ResponseEntity<?> mostChampionCrawling(Long startPlayerId, Long endPlayerId) {
        startTime = System.currentTimeMillis();
        failCount = 0;

        assignThreadTask(startPlayerId, endPlayerId, 5);

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

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        long currentStartPlayerId = startPlayerId;
        for (int i = 0; i < batchSizes.length; i++) {

            if (batchSizes[i] == 0)
                break;

            long currentEndPlayerId = currentStartPlayerId + batchSizes[i] - 1;

            CompletableFuture<Void> future = asyncService.processPlayersInRange(currentStartPlayerId, currentEndPlayerId);
            futures.add(future);

            currentStartPlayerId = currentEndPlayerId + 1;
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
            endTime = System.currentTimeMillis();
            System.out.println("startPlayerId = " + startPlayerId + " endPlayerId = " + endPlayerId + " 종료");
            System.out.println("성공 비율 = " + ((totalPlayers - failCount) * 100L / totalPlayers) + "%");
            System.out.println("소요 시간 = " + (endTime - startTime) / 1000 + "s");

        });

    }

}
