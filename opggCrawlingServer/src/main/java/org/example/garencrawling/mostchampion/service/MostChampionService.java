package org.example.garencrawling.mostchampion.service;

import org.springframework.http.ResponseEntity;

public interface MostChampionService {
    ResponseEntity<?> mostChampionCrawling() throws InterruptedException;
}
