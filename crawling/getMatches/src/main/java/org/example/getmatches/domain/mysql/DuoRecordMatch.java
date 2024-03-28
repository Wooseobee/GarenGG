package org.example.getmatches.domain.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "duo_record_match_id")
@NoArgsConstructor
@AllArgsConstructor
public class DuoRecordMatch {

    @EmbeddedId
    private DuoRecordMatchKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("duoRecordId")
    @JoinColumn(name = "duo_record_id", nullable = false)
    private DuoRecord duoRecord;
}
