//package gg.garen.back.champRecommendation.controller;
//
//import gg.garen.back.common.dto.ChampionDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController("/champRecommendation")
//@RequiredArgsConstructor
//public class champRecommendationController {
//
//    private final ChampRecommendationService champRecommendationService;
//
//    @GetMapping("/{summonerName}-{tagLine}")
//    ResponseEntity<List<ChampionDto>> championRecommend(@PathVariable String summonerName, String tagLine){
//
//        List<ChampionDto> response = champRecommendationService.championRecommend(summonerName, tagLine);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//}
