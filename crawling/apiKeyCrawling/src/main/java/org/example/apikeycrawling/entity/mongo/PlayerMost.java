package org.example.apikeycrawling.entity.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "player_most")
public class PlayerMost {
    @Id
    private Integer playerId;
    private String tier;
    private List<MostData> mostDatas;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MostData {
        private String mostSeq;
        private String champion;
        private String game;
    }
}
