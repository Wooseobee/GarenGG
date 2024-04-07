package org.example.getusermatches.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Info {
    private Long gameDuration;
    private List<Participant> participants;
}
