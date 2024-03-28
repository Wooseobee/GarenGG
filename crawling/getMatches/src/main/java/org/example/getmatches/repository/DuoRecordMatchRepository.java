package org.example.getmatches.repository;

import org.example.getmatches.domain.mysql.DuoRecordMatch;
import org.example.getmatches.domain.mysql.DuoRecordMatchKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DuoRecordMatchRepository extends JpaRepository<DuoRecordMatch, DuoRecordMatchKey> {
}
