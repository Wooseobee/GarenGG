package org.example.getmatches.domain.mysql;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Champion {
    @Id
    @Column(name = "champion_key")
    private String key;
    private String version;
    private String id;
    private String name;
    private String title;
    private String blurb;

    @Embedded
    private Info info;
    @Embedded
    private Image image;
    private String tags;
    private String partype;
    @Embedded
    private Stats stats;
}