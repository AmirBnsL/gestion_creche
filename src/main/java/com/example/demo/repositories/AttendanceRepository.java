package com.example.demo.repositories;

import com.example.demo.data.Attendance;
import com.example.demo.data.Child;
import com.example.demo.data.DayRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    List<Attendance> findByArrivalTimeBetween(LocalDateTime start, LocalDateTime end);

    Attendance findByChildAndArrivalTimeBetween(Child child, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Attendance> findByChildInAndArrivalTimeBetween(List<Child> children, LocalDateTime start, LocalDateTime end);

    Attendance findByChildAndDayRecord(Child child, DayRecord today);
}