package org.example.getmatches.repository;

import jakarta.persistence.LockModeType;
import org.example.getmatches.domain.mysql.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Match, String> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Match m where m.top= :top and m.jungle = :jungle and m.middle = :middle and m.bottom = :bottom and m.utility = :utility")
    Match findByWithPessimisticLock(Integer top, Integer jungle, Integer middle, Integer bottom, Integer utility);
}
