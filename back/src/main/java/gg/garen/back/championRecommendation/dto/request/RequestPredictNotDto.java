package gg.garen.back.championRecommendation.dto.request;

import gg.garen.back.common.domain.mongo.MostData;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestPredictNotDto {
    private Integer playerId;

    private String tier;

    private String rankNum = "0";

    private List<MostData> mostDatas;
}
