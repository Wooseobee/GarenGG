package org.example.garencrawling.mostchampion.repository;

import org.example.garencrawling.mostchampion.domain.Champion;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {
    List<Champion> findAll(Sort sort);
}
