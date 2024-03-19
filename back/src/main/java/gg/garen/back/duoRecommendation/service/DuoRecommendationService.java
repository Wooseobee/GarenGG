package gg.garen.back.duoRecommendation.service;

import gg.garen.back.duoRecommendation.dto.DuoRecommendationDto;

import java.util.List;

public interface DuoRecommendationService {
    List<DuoRecommendationDto> duoRecommend(String curChampion, String curPosition);
}
