package org.example.garencrawling.mostchampion.controller;

import lombok.RequiredArgsConstructor;
import org.example.garencrawling.global.GlobalConstants;
import org.example.garencrawling.mostchampion.service.MostChampionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/MostChampion")
public class MostChampionController {

    private final MostChampionService mostChampionService;

    @GetMapping("/crawling/{startPlayerId}/{endPlayerId}")
    public ResponseEntity<?> mostChampionCrawling(
            @PathVariable("startPlayerId") int startPlayerId,
            @PathVariable("endPlayerId") int endPlayerId) throws InterruptedException {

        StringBuilder sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date()))
                .append(" startPlayerId = ").append(startPlayerId).append(" endPlayerId = ").append(endPlayerId).append(" 시작");
        System.out.println(sb);

        mostChampionService.mostChampionCrawling(startPlayerId, endPlayerId);

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

}