package gg.garen.back.common.domain.mongo;

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

