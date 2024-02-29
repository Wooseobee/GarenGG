package org.example.getmatches.domain.mysql;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Info {
    private int attack;
    private int defense;
    private int magic;
    private int difficulty;
}