package org.example.garencrawling.mostchampion.domain.readmongo;

import lombok.*;
import org.example.garencrawling.mostchampion.domain.readmongo.Participant;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Info {
    private List<Participant> participants;
}
