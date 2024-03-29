package gg.garen.back.championPrediction.controller;

import gg.garen.back.championPrediction.service.ChampionPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/championPrediction")
public class ChampionPredictionController {
    private final ChampionPredictionService championPredictionService;

    @GetMapping("/start")
    ResponseEntity<?> getChampionPredictionStart() {
        return championPredictionService.getChampionPredictionStart();
    }

}