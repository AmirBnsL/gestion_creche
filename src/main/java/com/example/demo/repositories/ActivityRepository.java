package com.example.demo.repositories;


import com.example.demo.data.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    List<Activity> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}