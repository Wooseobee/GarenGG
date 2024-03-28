package gg.garen.back.common.domain.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "past_season_player_most")
public class PastSeasonPlayerMost {
    @Id
    private Integer playerId;

    private List<MostData> mostDatas;
}


