package org.example.getusermatches.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Info {
    private Long gameCreation;
    private Long gameDuration;
    private Long gameEndTimestamp;
    private Long gameId;
    private String gameMode;
    private String gameName;
    private Long gameStartTimestamp;
    private String gameType;
    private String gameVersion;
    private Integer mapId;
    private List<Participant> participants;
}
