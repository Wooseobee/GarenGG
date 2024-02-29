package org.example.getmatches.domain.mongo;

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
