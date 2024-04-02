package gg.garen.back.matchPrediction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParticipantDto {
    private byte[] enemyMissingPings;
    private byte[] championName;
    private byte[] individualPosition;
    private byte[] nickName;
    private byte[] riotIdTagline;
    private byte[] kills;
    private byte[] deaths;
    private byte[] assists;
    private byte[] firstBloodKill;
    private byte[] win;
}
