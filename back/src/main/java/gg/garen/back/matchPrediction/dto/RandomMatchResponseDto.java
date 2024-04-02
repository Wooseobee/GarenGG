package gg.garen.back.matchPrediction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RandomMatchResponseDto {
    private byte[] matchId;
    List<ParticipantDto> participants;
    private byte[] gameDuration;
    private String gameVersion;
    private byte[] secretKey;
    private byte[] match;
    private String tier;
}
