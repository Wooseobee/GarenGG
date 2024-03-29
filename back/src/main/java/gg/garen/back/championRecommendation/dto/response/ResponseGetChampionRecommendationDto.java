package gg.garen.back.championRecommendation.dto.response;

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

}