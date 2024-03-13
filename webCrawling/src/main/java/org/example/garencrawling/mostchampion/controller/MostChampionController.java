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

        System.out.println("현재 시간: " + GlobalConstants.formatter.format(new Date()) + " startPlayerId = " + startPlayerId + " endPlayerId" + endPlayerId + " 시작");
        mostChampionService.mostChampionCrawling(startPlayerId, endPlayerId);

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

}