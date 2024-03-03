package org.example.garencrawling.mostchampion.controller;

import lombok.RequiredArgsConstructor;
import org.example.garencrawling.mostchampion.service.MostChampionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/MostChampion")
public class MostChampionController {

    private final MostChampionService mostChampionService;

    @GetMapping("/crawling/{start_player_id}/{end_player_id}")
    public ResponseEntity<?> mostChampionCrawling(
            @PathVariable("start_player_id") Long startPlayerId
            , @PathVariable("end_player_id") Long endPlayerId) {
        return mostChampionService.mostChampionCrawling(startPlayerId, endPlayerId);
    }
}