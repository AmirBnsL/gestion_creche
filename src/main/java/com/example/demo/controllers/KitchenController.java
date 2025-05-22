package com.example.demo.controllers;

import com.example.demo.data.Child;

import com.example.demo.data.DayRecord;
import com.example.demo.data.Meal;
import com.example.demo.data.MealConsumption;
import com.example.demo.enums.ChildNeeds;
import com.example.demo.enums.MealType;
import com.example.demo.repositories.ChildRepository;
import com.example.demo.services.DayRecordService;
import com.example.demo.services.KitchenService;
import com.example.demo.repositories.DayRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/kitchen")
@AllArgsConstructor
public class KitchenController {

    private final KitchenService kitchenService;
    private final DayRecordRepository dayRecordRepository;
    private final ChildRepository childRepository;
    private final DayRecordService dayRecordService;

    @GetMapping("/home")
    public String kitchenHome() {
        return "kitchen/kitchen-home";
    }

    @GetMapping("/add-meals")
    public String addMealsPage(Model model) {
        LocalDate today = LocalDate.now();
        DayRecord dayRecord = dayRecordRepository.findByDate(today)
                .orElseGet(() -> {
                    DayRecord newRecord = new DayRecord();
                    newRecord.setDate(today);
                    return dayRecordRepository.save(newRecord);
                });

        // Ensure the three meals exist for the day
        kitchenService.ensureDailyMeals(dayRecord);

        model.addAttribute("meals", kitchenService.getMealsForDay(dayRecord));
        return "kitchen/add-meals";
    }

    @PostMapping("/add-meals")
    public String addMeal(@RequestParam MealType mealType, @RequestParam String description, @RequestParam(required = false) Set<ChildNeeds> supportedNeeds) {
        kitchenService.addMealForToday(mealType, description,supportedNeeds);
        return "redirect:/kitchen/add-meals";
    }

    @GetMapping("/mark-meals")
    public String markMealsPage(Model model) {
        DayRecord dayRecord = dayRecordService.getOrCreateTodayRecord();
        List<Meal> meals = kitchenService.getMealsForDay(dayRecord);
        model.addAttribute("children", childRepository.findAll());
        model.addAttribute("mealTypes", MealType.values());
        model.addAttribute("meals", meals);

        // Group MealConsumption by MealType
        Map<MealType, List<MealConsumption>> groupedMealHistory = kitchenService.getMealConsumptionsForToday()
                .stream()
                .collect(Collectors.groupingBy(consumption -> consumption.getMeal().getMealType()));

        model.addAttribute("mealHistory", groupedMealHistory);
        return "kitchen/mark-meals";
    }

    @PostMapping("/mark-meals")
    public String markMeal(@RequestParam Long childId, @RequestParam MealType mealType) {
        Child child = childRepository.findById(childId).orElseThrow();
        kitchenService.markMealForChild(child, mealType, LocalTime.now());
        return "redirect:/kitchen/mark-meals";
    }
}