//package gg.garen.back.duoRecommendation.controller;
//
//import gg.garen.back.duoRecommendation.dto.duoRecommendationResponseDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.*;
//
//@RestController("/duoRecommendation")
//public class duoRecommendationController {
//
//    private final DuoRecommendationService duoRecommendationService;
//
//    @GetMapping("/{summonerName}-{tagLine}")
//    ResponseEntity<List<duoRecommendationResponseDto>> duoRecommend(@PathVariable String summonerName, String tagLine){
//        List<duoRecommendationResponseDto> response = duoRecommendationService.duoRecommend(summonerName, tagLine);
//
//        return new ResponseEntity<List<duoRecommendationResponseDto>>(response, HttpStatus.OK);
//    }
//}
