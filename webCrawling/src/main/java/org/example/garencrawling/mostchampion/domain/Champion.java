package org.example.garencrawling.mostchampion.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "champion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Champion {
    @Id
    private Long championKey;

    private String id;

    private String name;

}
