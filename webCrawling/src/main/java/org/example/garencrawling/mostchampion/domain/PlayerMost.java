package org.example.garencrawling.mostchampion.domain;

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

    private String rankNum;

    private List<MostData> mostDatas;
}
