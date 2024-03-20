package org.example.garencrawling.mostchampion.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant {
    private String championName;
    private String puuid;
    private Boolean win;
}
