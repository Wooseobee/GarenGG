package org.example.garencrawling.mostchampion.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Info {
    private List<Participant> participants;
}
