package com.example.demo.repositories;

import com.example.demo.data.DayRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DayRecordRepository extends JpaRepository<DayRecord, Long> {
    Optional<DayRecord> findByDate(LocalDate date);
}
