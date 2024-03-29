package gg.garen.back.championRecommendation.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampionRecommendationData {
    private String id;
    private String name;
    private Integer difficulty;
    private Double Predictions;
}
