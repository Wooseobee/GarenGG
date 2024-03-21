package gg.garen.back.championRecommendation.dto.response;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseGetChampionRecommendationDto {

    private List<ChampionRecommendationData> championRecommendationDatas;

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChampionRecommendationData {
        private String id; // 예: "Xerath"
        private String name; // 예: "제라스"
        private Integer difficulty; // 예: 8
        private Double predictions; // 예: 1.7053412848125777
    }
}