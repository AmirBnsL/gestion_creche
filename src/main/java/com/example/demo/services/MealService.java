package com.example.demo.services;


import com.example.demo.data.*;
import com.example.demo.repositories.MealConsumptionRepository;
import com.example.demo.repositories.MealRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MealService {
    private final MealConsumptionRepository mealConsumptionRepository;
    private final DayRecordService dayRecordService;
    private final MealRepository mealRepository;

    public List<Meal> getTodayMealsForChild(Child child) {
        DayRecord today = dayRecordService.getOrCreateTodayRecord();
        return mealConsumptionRepository.findByChildAndMeal_DayRecord(child, today)
                .stream()
                .map(MealConsumption::getMeal)
                .collect(Collectors.toList());
    }
    public List<Activity> getTodayActivitiesForChild(Child child) {
        DayRecord today = dayRecordService.getOrCreateTodayRecord();
        return today.getActivities().stream()
                .filter(act -> act.getParticipants().contains(child))
                .collect(Collectors.toList());
    }

    public List<MealConsumption> getTodayMealConsumptionsForChild(Child child) {
        return mealConsumptionRepository.findByChildAndMeal_DayRecord(child, dayRecordService.getOrCreateTodayRecord());
    }


}