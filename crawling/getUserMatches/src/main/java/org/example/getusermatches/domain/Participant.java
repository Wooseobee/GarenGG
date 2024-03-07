package org.example.getusermatches.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Participant {
    private int championId;
    private String championName;
    private int deaths;
    private String individualPosition;
    private int kills;
    private String lane;
    private int participantId;
    private String puuid;
    private String role;
    private String summonerId;
    private int summonerLevel;
    private String summonerName;
    private boolean win;

    // 생성자, 게터, 세터 등
}
