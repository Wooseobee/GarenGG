package gg.garen.back.common.domain.mongo;

import gg.garen.back.common.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "player_match")
@Getter
@Setter
public class MatchInfo extends Auditable {

    @Id
    private String matchId;
    private Metadata metadata;
    private Info info;
}
