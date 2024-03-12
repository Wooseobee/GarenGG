package gg.garen.back.duoRecommendation.controller;

import gg.garen.back.duoRecommendation.dto.DuoRecommendationDto;
import gg.garen.back.duoRecommendation.service.DuoRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("/duoRecommendation")
@RequiredArgsConstructor
public class DuoRecommendationController {

    private final DuoRecommendationService duoRecommendationService;

    //최대 15마리의 챔피언을 가져온다.
    @GetMapping("/{champion}-{position}")
    ResponseEntity<List<DuoRecommendationDto>> duoRecommend(@PathVariable String champion, @PathVariable String position){

        //각 dto에는 3개의챔피언을 갖는다.
        List<DuoRecommendationDto> response = duoRecommendationService.duoRecommend(champion, position);

        if(response == null){
            // 에러리턴.
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
