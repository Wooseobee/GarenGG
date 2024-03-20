package gg.garen.back.champion.controller;


import gg.garen.back.champion.dto.ChampionDto;
import gg.garen.back.champion.service.ChampionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/champions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChampionController {

    private final ChampionService championService;
    @GetMapping
    public ResponseEntity<List<ChampionDto>> championInfo(){
        List<ChampionDto> list = championService.championInfo();
        log.info("ChampList : {}", list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
