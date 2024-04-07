package org.example.getmatches.repository;

import jakarta.persistence.LockModeType;
import org.example.getmatches.domain.mysql.DuoRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface DuoRecordRepository extends JpaRepository<DuoRecord, String> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select d from DuoRecord d where d.champion1= :champion1 and d.lane1 = :lane1 and d.champion2 = :champion2 and d.lane2= :lane2")
    DuoRecord findByWithPessimisticLock(Long champion1, String lane1, Long champion2, String lane2);
}
