package org.example.garencrawling.mostchampion.domain.readmongo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "player_match")
public class PlayerMatch {

    private Info info;
}
