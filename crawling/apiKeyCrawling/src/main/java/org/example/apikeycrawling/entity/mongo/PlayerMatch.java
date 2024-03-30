package org.example.apikeycrawling.entity.mongo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "player_match")
public class PlayerMatch {

    private Info info;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Info {
        private List<Participant> participants;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Participant {
        private String championName;
        private String riotIdGameName;
        private String riotIdTagline;
        private Boolean win;
    }
}