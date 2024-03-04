package org.example.getmatches.repository;

import jakarta.persistence.LockModeType;
import org.example.getmatches.domain.mysql.Combination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface CombinationRepository extends JpaRepository<Combination, String> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Combination m where m.top= :top and m.jungle = :jungle and m.middle = :middle and m.bottom = :bottom and m.utility = :utility")
    Combination findByWithPessimisticLock(Integer top, Integer jungle, Integer middle, Integer bottom, Integer utility);
}
