package org.example.garencrawling.mostchampion.domain.writemongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MostData {

    private String mostSeq;
    private String champion;
    private String game;

}
