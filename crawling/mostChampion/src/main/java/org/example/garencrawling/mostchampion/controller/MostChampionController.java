package org.example.garencrawling.mostchampion.controller;

import lombok.RequiredArgsConstructor;
import org.example.garencrawling.mostchampion.service.MostChampionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mostChampion")
public class MostChampionController {

    private final MostChampionService mostChampionService;

    @GetMapping
    public ResponseEntity<?> calculateMostChampion() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb;

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date())).append(" 시작");
        System.out.println(sb);

        mostChampionService.calculateMostChampion();

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(formatter.format(new Date())).append(" 끝남");
        System.out.println(sb);

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

}