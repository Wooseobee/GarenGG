package gg.garen.back.championRecommendation.service;

import gg.garen.back.championRecommendation.dto.response.ResponseGetChampionRecommendationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChampionRecommendationServiceImpl implements ChampionRecommendationService {
    @Override
    public ResponseGetChampionRecommendationDto getChampionRecommendation(String summonerName, String tagLine) {
        return null;
    }
}
