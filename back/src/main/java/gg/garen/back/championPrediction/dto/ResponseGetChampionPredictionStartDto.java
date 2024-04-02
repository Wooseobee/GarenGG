package gg.garen.back.championPrediction.dto;

import gg.garen.back.champion.dto.ChampionDto;
import gg.garen.back.champion.entity.Champion;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseGetChampionPredictionStartDto {

    private String encryptedData;
    private String secretKey;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Round {
        private ChampionDto answerChampion;
        private List<ChampionDto> candidateChampions;
    }


}
