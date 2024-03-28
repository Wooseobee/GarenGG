package gg.garen.back.championRecommendation.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPredictDto {
    private Integer playerId;
    private String tier;
}
