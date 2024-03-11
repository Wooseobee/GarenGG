package org.example.getmatches.domain.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "combination", indexes = {
        @Index(name = "idx_champion1", columnList = "champion1, lane1"),
        @Index(name = "idx_champion2", columnList = "champion2, lane2")
})
@ToString
public class Combination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer champion1;

    @Column(nullable = false)
    private String lane1;

    @Column(nullable = false)
    private Integer champion2;

    @Column(nullable = false)
    private String lane2;

    private Long victory = 0L;
    private Long defeat = 0L;
    private Long totalMatch = 0L;

    private Double winRate = 0.0;
}
