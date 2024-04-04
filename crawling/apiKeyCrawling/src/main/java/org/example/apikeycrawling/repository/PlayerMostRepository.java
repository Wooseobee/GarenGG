package org.example.apikeycrawling.repository;

import org.example.apikeycrawling.entity.mongo.PlayerMost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerMostRepository extends MongoRepository<PlayerMost, Integer> {

    Page<PlayerMost> findAll(Pageable pageable);
}

