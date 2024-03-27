package gg.garen.back.championRecommendation.service;

import org.springframework.http.ResponseEntity;

public interface ChampionRecommendationService {
    ResponseEntity<?> getChampionRecommendation(String summonerName, String tagLine);
}
