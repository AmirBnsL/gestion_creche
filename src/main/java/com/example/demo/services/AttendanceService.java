package com.example.demo.services;

import com.example.demo.data.Attendance;
import com.example.demo.data.Child;
import com.example.demo.data.DayRecord;
import com.example.demo.repositories.AttendanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final DayRecordService dayRecordService;

    public Attendance getTodayAttendanceForChild(Child child) {
        DayRecord today = dayRecordService.getOrCreateTodayRecord();
        return attendanceRepository.findByChildAndDayRecord(child, today);
    }


}