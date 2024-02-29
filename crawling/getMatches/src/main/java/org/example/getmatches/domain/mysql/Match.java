package org.example.getmatches.domain.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "match_data")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer top;

    private Integer jungle;

    private Integer middle;

    private Integer bottom;

    private Integer utility;

    private Long victory;
    private Long defeat;
}
