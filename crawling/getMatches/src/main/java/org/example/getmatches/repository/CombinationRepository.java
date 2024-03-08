package org.example.getmatches.repository;

import jakarta.persistence.LockModeType;
import org.example.getmatches.domain.mysql.Combination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface CombinationRepository extends JpaRepository<Combination, String> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Combination m where m.champion1= :champion1 and m.lane1 = :lane1 and m.champion2 = :champion2 and m.lane2= :lane2")
    Combination findByWithPessimisticLock(Integer champion1, String lane1, Integer champion2, String lane2);
}
