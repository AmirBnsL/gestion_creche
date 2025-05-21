package com.example.demo.repositories;

import com.example.demo.data.DevelopmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevelopmentRecordRepository extends JpaRepository<DevelopmentRecord,Long> {
    List<DevelopmentRecord> findByChildId(Long childId);
}