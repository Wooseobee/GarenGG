package org.example.garencrawling.mostchampion.repository;

import org.example.garencrawling.mostchampion.domain.PlayerMatch;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface PlayerMatchRepository extends MongoRepository<PlayerMatch, String> {
    List<PlayerMatch> findByMatchIdIn(Collection<String> matchIds);
}
