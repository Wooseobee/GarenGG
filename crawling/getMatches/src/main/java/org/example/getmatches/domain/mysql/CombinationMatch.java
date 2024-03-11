package org.example.getmatches.domain.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "combination_match_id")
@NoArgsConstructor
@AllArgsConstructor
public class CombinationMatch {

    @EmbeddedId
    private CombinationMatchKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("combinationId")
    @JoinColumn(name = "combination_id", nullable = false)
    private Combination combination;
}
