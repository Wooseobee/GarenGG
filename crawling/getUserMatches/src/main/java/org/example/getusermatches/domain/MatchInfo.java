package org.example.getusermatches.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "player_match")
@Getter
@Setter
public class MatchInfo {

    private String matchId;
    private Metadata metadata;
    private Info info;
}
