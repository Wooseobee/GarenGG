package org.example.getmatches.domain.mysql;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Stats {
    private double hp;
    private double hpperlevel;
    private double mp;
    private double mpperlevel;
    private int movespeed;
    private double armor;
    private double armorperlevel;
    private double spellblock;
    private double spellblockperlevel;
    private int attackrange;
    private double hpregen;
    private double hpregenperlevel;
    private double mpregen;
    private double mpregenperlevel;
    private int crit;
    private double critperlevel;
    private double attackdamage;
    private double attackdamageperlevel;
    private double attackspeedperlevel;
    private double attackspeed;
}
