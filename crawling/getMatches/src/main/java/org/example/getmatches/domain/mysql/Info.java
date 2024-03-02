package org.example.getmatches.domain.mysql;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    private Long attack;
    private Long defense;
    private Long magic;
    private Long difficulty;
}