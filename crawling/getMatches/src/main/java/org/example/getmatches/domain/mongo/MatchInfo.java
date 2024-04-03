package org.example.getmatches.domain.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "player_match_v1")
@Getter
@Setter
public class MatchInfo {

    @Id
    private String matchId;
    private Metadata metadata;
    private Info info;
}
