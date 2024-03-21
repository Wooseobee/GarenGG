package gg.garen.back.champion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Champion {
    @Id
    @Column(name = "champion_key")
    private Long key;
    private String version;
    private String id;
    private String name;
    private String title;
    private String blurb;

    @Embedded
    private Info info;
    @Embedded
    private Image image;

    @ElementCollection
    private List<String> tags;
    private String partype;
    @Embedded
    private Stats stats;
}
