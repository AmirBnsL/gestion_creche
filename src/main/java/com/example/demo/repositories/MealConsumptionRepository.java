package com.example.demo.repositories;

import com.example.demo.data.Child;
import com.example.demo.data.DayRecord;
import com.example.demo.data.Meal;
import com.example.demo.data.MealConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealConsumptionRepository extends JpaRepository<MealConsumption, Long> {
    List<MealConsumption> findByMealDate(LocalDate date);

    boolean existsByMealAndChild(Meal meal, Child child);

    List<MealConsumption> findByChildAndMeal_DayRecord(Child child, DayRecord meal_dayRecord);
}