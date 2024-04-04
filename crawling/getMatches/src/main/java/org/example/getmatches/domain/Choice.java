package org.example.getmatches.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Choice {
    String championName;
    Integer championId;
    String lane;

    public Choice(String championName, Integer championId, String lane) {
        this.championName = championName;
        this.championId = championId;
        this.lane = lane;
    }
}