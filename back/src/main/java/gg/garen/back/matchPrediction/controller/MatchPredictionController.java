package gg.garen.back.matchPrediction.controller;

import gg.garen.back.matchPrediction.dto.RandomMatchResponseDto;
import gg.garen.back.matchPrediction.service.MatchPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchPredictionController {

    private final MatchPredictionService matchPredictionService;

    @GetMapping("/randomMatch")
    public RandomMatchResponseDto getRandomMatch() {
        return matchPredictionService.getRandomMatch();
    }
}
