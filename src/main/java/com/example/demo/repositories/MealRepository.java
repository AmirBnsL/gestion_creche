package com.example.demo.repositories;


import com.example.demo.data.Child;
import com.example.demo.data.DayRecord;
import com.example.demo.data.Meal;
import com.example.demo.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByDayRecord(DayRecord dayRecord);

    Optional<Meal> findByDayRecordAndMealType(DayRecord dayRecord, MealType mealType);

    Optional<Meal> findByDateAndMealType(LocalDate date, MealType mealType);


}