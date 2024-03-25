package gg.garen.back.championRecommendation.service;

import gg.garen.back.championRecommendation.dto.response.ResponseGetChampionRecommendationDto;

public interface ChampionRecommendationService {
    ResponseGetChampionRecommendationDto getChampionRecommendation(String summonerName, String tagLine);
}
