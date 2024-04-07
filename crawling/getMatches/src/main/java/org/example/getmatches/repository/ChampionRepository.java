package org.example.getmatches.repository;

import org.example.getmatches.domain.mysql.Champion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepository extends JpaRepository<Champion, Long> {
}
