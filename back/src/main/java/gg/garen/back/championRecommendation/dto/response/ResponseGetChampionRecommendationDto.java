package gg.garen.back.championRecommendation.dto.response;

import gg.garen.back.championRecommendation.dto.request.RequestPredictNotDto;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseGetChampionRecommendationDto {

    private String errorMessage;
    private List<ChampionRecommendationData> championRecommendationDatas;
    private String tier;
    private List<RequestPredictNotDto.MostData> mostDatas;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChampionRecommendationData {
        private String id;
        private String name;
        private Integer difficulty;
        private Double Predictions;
    }
}