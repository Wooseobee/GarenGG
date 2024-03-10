package org.example.garencrawling.mostchampion.service;

import org.springframework.http.ResponseEntity;

public interface MostChampionService {
    void mostChampionCrawling(int startPlayerId, int endPlayerId) throws InterruptedException;
}
