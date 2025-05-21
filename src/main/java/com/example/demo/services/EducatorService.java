package com.example.demo.services;

import com.example.demo.data.*;
import com.example.demo.repositories.ActivityRepository;
import com.example.demo.repositories.AttendanceRepository;
import com.example.demo.repositories.ChildRepository;
import com.example.demo.repositories.DevelopmentRecordRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EducatorService {

    private final AttendanceRepository attendanceRepository;
    private final ChildRepository childRepository;
    private final DayRecordService dayRecordService;
    private final ActivityRepository activityRepository;
    private final DevelopmentRecordRepository developmentRecordRepository;

    public void recordArrival(Long childId) {
        Child child = childRepository.findById(childId).orElseThrow();
        DayRecord today = dayRecordService.getOrCreateTodayRecord();

        boolean alreadyPresent = today.getAttendances().stream()
                .anyMatch(a -> a.getChild().getId().equals(childId));
        if (alreadyPresent) return;

        Attendance attendance = new Attendance();
        attendance.setArrivalTime(LocalDateTime.now().toLocalTime());
        attendance.setChild(child);
        attendance.setDayRecord(today);

        attendanceRepository.save(attendance);
    }

    public void recordDeparture(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow();
        attendance.setDepartureTime(LocalDateTime.now().toLocalTime());
        attendanceRepository.save(attendance);
    }

    public void scheduleActivity(Activity act) {
        DayRecord today = dayRecordService.getOrCreateTodayRecord();
        act.setDayRecord(today);
        activityRepository.save(act);
    }

    public void addRecord(DevelopmentRecord dr) {
        dr.setDate(LocalDate.now());
        developmentRecordRepository.save(dr);
    }

    public List<DevelopmentRecord> getRecordsForChild(Long childId) {
        return developmentRecordRepository.findByChildId(childId);
    }
}

