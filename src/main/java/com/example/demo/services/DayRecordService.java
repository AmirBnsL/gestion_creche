package com.example.demo.services;

import com.example.demo.data.DayRecord;
import com.example.demo.repositories.DayRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DayRecordService {

    private final DayRecordRepository dayRecordRepository;

    public DayRecord getOrCreateTodayRecord() {
        LocalDate today = LocalDate.now();
        return dayRecordRepository.findByDate(today)
                .orElseGet(() -> {
                    DayRecord dayRecord = new DayRecord();
                    dayRecord.setDate(today);
                    dayRecordRepository.save(dayRecord);
                    return dayRecord;
                });
    }
}
