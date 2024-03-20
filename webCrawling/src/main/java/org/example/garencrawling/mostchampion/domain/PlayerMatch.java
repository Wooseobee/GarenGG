package org.example.garencrawling.mostchampion.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "player_match")
public class PlayerMatch {
    @Id
    private String matchId;
    private Info info;
}
