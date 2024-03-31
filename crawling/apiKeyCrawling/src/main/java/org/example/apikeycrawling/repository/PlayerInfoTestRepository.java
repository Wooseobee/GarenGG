package org.example.apikeycrawling.repository;

import org.example.apikeycrawling.entity.mysql.PlayerInfoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerInfoTestRepository extends JpaRepository<PlayerInfoTest, Integer>, PlayerInfoTestCustomRepository {

    Page<PlayerInfoTest> findAll(Pageable pageable);
}
