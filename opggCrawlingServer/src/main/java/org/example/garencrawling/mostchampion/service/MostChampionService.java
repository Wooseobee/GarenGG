package org.example.garencrawling.mostchampion.service;

import org.springframework.http.ResponseEntity;

public interface MostChampionService {
    ResponseEntity<?> mostChampionCrawling(Long startPlayerId, Long endPlayerId);
}
