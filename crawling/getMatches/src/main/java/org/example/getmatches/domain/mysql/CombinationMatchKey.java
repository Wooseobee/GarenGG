package org.example.getmatches.domain.mysql;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinationMatchKey implements Serializable {
    private Long combinationId;
    private String matchId;
}
