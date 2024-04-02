package gg.garen.back.matchPrediction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RandomMatchResponseDto {
    private byte[] matchId;
    private byte[] participants;
    private byte[] gameDuration;
    private byte[] matchInfo;
    private byte[] match;
    private String tier;
}
