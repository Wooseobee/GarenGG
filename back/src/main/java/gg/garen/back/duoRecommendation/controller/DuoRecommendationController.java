package gg.garen.back.duoRecommendation.controller;

import gg.garen.back.duoRecommendation.dto.DuoRecommendationDto;
import gg.garen.back.duoRecommendation.service.DuoRecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/duoRecommendation")
@RequiredArgsConstructor
public class DuoRecommendationController {

    private final DuoRecommendationService duoRecommendationService;

    //최대 15마리의 챔피언을 가져온다.
    @GetMapping("/{champion}-{position}")
    ResponseEntity<List<DuoRecommendationDto>> duoRecommend(@PathVariable String champion, @PathVariable String position){
        log.info("controller. request : {} {}", champion, position);
        //각 dto에는 3개의챔피언을 갖는다.
        List<DuoRecommendationDto> response = duoRecommendationService.duoRecommend(champion, position.toUpperCase());
        log.info("controller. response : {}", response);
        if (response == null) {
            // 에러리턴.
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/health-check")
    String healthCheck(){
        return "I am healthy";
    }
}
