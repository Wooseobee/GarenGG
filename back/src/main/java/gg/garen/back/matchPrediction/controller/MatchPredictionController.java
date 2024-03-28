package gg.garen.back.matchPrediction.controller;

import gg.garen.back.matchPrediction.dto.RandomMatchResponseDto;
import gg.garen.back.matchPrediction.service.MatchPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MatchPredictionController {

    private final MatchPredictionService matchPredictionService;

    @GetMapping("/randomMatch")
    public RandomMatchResponseDto getRandomMatch() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();

        return matchPredictionService.getRandomMatch(secretKey);
    }
}
