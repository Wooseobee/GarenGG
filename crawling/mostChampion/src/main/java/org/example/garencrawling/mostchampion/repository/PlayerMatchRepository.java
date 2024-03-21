package org.example.garencrawling.mostchampion.repository;

import org.example.garencrawling.mostchampion.domain.readmongo.PlayerMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerMatchRepository extends MongoRepository<PlayerMatch, String> {

    Page<PlayerMatch> findAll(Pageable pageable);
}
