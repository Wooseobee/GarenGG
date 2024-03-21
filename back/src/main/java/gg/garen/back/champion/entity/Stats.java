package gg.garen.back.champion.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stats {
    private double hp;
    private double hpperlevel;
    private double mp;
    private double mpperlevel;
    private double movespeed;
    private double armor;
    private double armorperlevel;
    private double spellblock;
    private double spellblockperlevel;
    private double attackrange;
    private double hpregen;
    private double hpregenperlevel;
    private double mpregen;
    private double mpregenperlevel;
    private double crit;
    private double critperlevel;
    private double attackdamage;
    private double attackdamageperlevel;
    private double attackspeedperlevel;
    private double attackspeed;
}
