package org.example.getmatches.repository;

import org.example.getmatches.domain.mysql.CombinationMatch;
import org.example.getmatches.domain.mysql.CombinationMatchKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CombinationMatchRepository extends JpaRepository<CombinationMatch, CombinationMatchKey> {
}
