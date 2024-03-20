package org.example.garencrawling.mostchampion.domain.readmongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant {
    private String puuid;
    private String championName;
    private Boolean win;
}
