package com.example.demo.controllers;

import com.example.demo.data.*;
import com.example.demo.repositories.AttendanceRepository;
import com.example.demo.repositories.ChildRepository;
import com.example.demo.services.ChildService;
import com.example.demo.services.DayRecordService;
import com.example.demo.services.EducatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/educator")
@AllArgsConstructor
public class EducatorController {

    private final EducatorService eduService;
    private final ChildRepository childRepo;
    private final ChildService childService;
    private final DayRecordService dayRecordService;

    @GetMapping("/home")
    public String home() {
        return "educator/home";
    }

    @GetMapping("/attendance")
    public String attendancePage(Model model) {
        List<Child> children = childService.getAll();
        DayRecord todayRecord = dayRecordService.getOrCreateTodayRecord();

        Map<Long, Attendance> attendanceMap = new HashMap<>();
        if (todayRecord.getAttendances() != null) {
            for (Attendance a : todayRecord.getAttendances()) {
                attendanceMap.put(a.getChild().getId(), a);
            }
        }
        model.addAttribute("attendanceMap", attendanceMap);
        model.addAttribute("activity", new Activity());
        model.addAttribute("children", childService.getAll());
        model.addAttribute("activityList", todayRecord.getActivities());
        model.addAttribute("today", todayRecord.getDate());
        return "educator/attendance";
    }

    @PostMapping("/attendance/arrive/{childId}")
    public String arrive(@PathVariable Long childId) {
        eduService.recordArrival(childId);
        return "redirect:/educator/attendance";
    }

    @PostMapping("/attendance/depart/{attendanceId}")
    public String depart(@PathVariable Long attendanceId) {
        eduService.recordDeparture(attendanceId);
        return "redirect:/educator/attendance";
    }

    @GetMapping("/activities")
    public String activities(Model m) {
        List<Child> children = childService.getAvailableChildren();
        m.addAttribute("activity", new Activity());
        m.addAttribute("children", childService.getAvailableChildren());
        m.addAttribute("activityList", dayRecordService.getOrCreateTodayRecord().getActivities());
        m.addAttribute("today", LocalDate.now());
        return "educator/activities";
    }

    @PostMapping("/activities")
    public String schedule(@ModelAttribute Activity act) {
        eduService.scheduleActivity(act);
        return "redirect:/educator/activities";
    }

    @GetMapping("/development/{childId}")
    public String devForm(@PathVariable Long childId, Model m) {
        Collection<DevelopmentRecord> developmentRecords = eduService.getRecordsForChild(childId);
        DevelopmentRecord developmentRecord = new DevelopmentRecord();
        developmentRecord.setChild(childRepo.findById(childId).orElseThrow());
        m.addAttribute("record", developmentRecord);
        m.addAttribute("child", developmentRecord.getChild());
        m.addAttribute("developments", developmentRecords);
        return "educator/development";
    }

    @PostMapping("/development")
    public String submitDev(@ModelAttribute DevelopmentRecord dr) {
        eduService.addRecord(dr);
        return "redirect:/educator/development/" + dr.getChild().getId();
    }
    @GetMapping("/development")
    public String developmentList(Model model) {
        List<Child> children = childService.getAll();
        model.addAttribute("children", children);
        return "educator/development_list";
    }
}
