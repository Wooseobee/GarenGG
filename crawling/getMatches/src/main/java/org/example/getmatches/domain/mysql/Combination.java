package org.example.getmatches.domain.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "combination", indexes = {
        @Index(name = "idx_win_rate", columnList = "winRate"),
        @Index(name = "idx_champion1", columnList = "champion1"),
        @Index(name = "idx_champion2", columnList = "champion2"),
        @Index(name = "idx_champion3", columnList = "champion3"),
        @Index(name = "idx_champion4", columnList = "champion4"),
        @Index(name = "idx_champion5", columnList = "champion5")
})
@ToString
public class Combination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer top;

    @Column(nullable = false)
    private Integer jungle;

    @Column(nullable = false)
    private Integer middle;

    @Column(nullable = false)
    private Integer bottom;

    @Column(nullable = false)
    private Integer utility;

    private Long victory = 0L;
    private Long defeat = 0L;

    private Double winRate = 0.0;
}
