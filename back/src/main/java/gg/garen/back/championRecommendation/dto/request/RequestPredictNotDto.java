package gg.garen.back.championRecommendation.dto.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestPredictNotDto {
    private String tier;
    private List<MostData> mostDatas;
    private String rankNum = "0";

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MostData {
        private String mostSeq;
        private String champion;
        private String game;
    }
}
