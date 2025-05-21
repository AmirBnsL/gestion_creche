package com.example.demo.services;

import com.example.demo.data.Child;
import com.example.demo.data.DayRecord;
import com.example.demo.data.Meal;
import com.example.demo.data.MealConsumption;
import com.example.demo.enums.MealType;
import com.example.demo.repositories.ChildRepository;
import com.example.demo.repositories.MealConsumptionRepository;
import com.example.demo.repositories.MealRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Service
@AllArgsConstructor
public class KitchenService {

    private final MealRepository mealRepository;

    private final MealConsumptionRepository mealConsumptionRepository;
    private final DayRecordService dayRecordService;

    public List<Meal> getMealsForDay(DayRecord dayRecord) {
        return mealRepository.findByDayRecord(dayRecord);
    }

    public void ensureDailyMeals(DayRecord dayRecord) {
        for (MealType type : MealType.values()) {
            if (mealRepository.findByDayRecordAndMealType(dayRecord, type).isEmpty()) {
                Meal meal = new Meal();
                meal.setDate(dayRecord.getDate());
                meal.setMealType(type);
                meal.setDayRecord(dayRecord);
                mealRepository.save(meal);
            }
        }
    }

    public void addMealForToday(MealType mealType, String description) {
        DayRecord today = dayRecordService.getOrCreateTodayRecord();
        Meal meal = mealRepository.findByDayRecordAndMealType(today, mealType)
                .orElseGet(() -> {
                    Meal newMeal = new Meal();
                    newMeal.setDate(today.getDate());
                    newMeal.setMealType(mealType);
                    newMeal.setDayRecord(today);
                    return newMeal;
                });
        meal.setDescription(description);
        mealRepository.save(meal);
    }

    public List<MealConsumption> getMealConsumptionsForToday() {
        LocalDate today = LocalDate.now();
        return mealConsumptionRepository.findByMealDate(today);
    }

    public void markMealForChild(Child child, MealType mealType, LocalTime time) {
        Meal meal = mealRepository.findByDateAndMealType(LocalDate.now(), mealType)
                .orElseThrow(() -> new IllegalStateException("Meal not found"));

        boolean alreadyConsumed = mealConsumptionRepository.existsByMealAndChild(meal, child);
        if (alreadyConsumed) {
            throw new IllegalStateException("Child has already consumed this meal today.");
        }

        MealConsumption consumption = new MealConsumption();
        consumption.setMeal(meal);
        consumption.setChild(child);
        consumption.setTime(time);
        mealConsumptionRepository.save(consumption);
    }
}