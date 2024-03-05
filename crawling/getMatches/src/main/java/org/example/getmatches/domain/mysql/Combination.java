package org.example.getmatches.domain.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "combination")
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
}
