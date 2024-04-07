package gg.garen.back.champion.repository;

import gg.garen.back.champion.entity.Champion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepository extends JpaRepository<Champion,Long> {

}
