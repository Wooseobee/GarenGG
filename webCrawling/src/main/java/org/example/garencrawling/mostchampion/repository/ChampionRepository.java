package org.example.garencrawling.mostchampion.repository;

import org.example.garencrawling.mostchampion.domain.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {
}
