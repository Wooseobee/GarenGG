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

    @GetMapping("/crawling")
    public ResponseEntity<?> mostChampionCrawling() {
        return mostChampionService.mostChampionCrawling();
    }
}