package org.example.getusermatches.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "player_matchV2")
@Getter
@Setter
public class MatchInfo {

    private String matchId;
    private Metadata metadata;
    private Info info;
    private String tier;
    private String rank;
}
