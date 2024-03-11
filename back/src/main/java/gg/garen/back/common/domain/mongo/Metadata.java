package gg.garen.back.common.domain.mongo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Metadata {
    private String dataVersion;
    private String matchId;
    private List<String> participants;
}
