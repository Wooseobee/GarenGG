package gg.garen.back.common.domain.mongo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Participant {
    private int assistMePings;
    private int championId;
    private String championName;
    private int deaths;
    private int enemyMissingPings;
    private boolean firstBloodKill;
    private String individualPosition;
    private int kills;
    private String lane;
    private int participantId;
    private String puuid;
    private String riotIdTagline;
    private String role;
    private String summonerId;
    private String summonerName;
    private int totalDamageDealtToChampions;
    private int trueDamageDealtToChampions;
    private boolean win;
}
