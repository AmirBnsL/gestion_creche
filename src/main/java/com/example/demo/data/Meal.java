package com.example.demo.data;

import com.example.demo.enums.ChildNeeds;
import com.example.demo.enums.MealType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private MealType mealType;

    @ElementCollection(targetClass = ChildNeeds.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "meal_supported_needs", joinColumns = @JoinColumn(name = "meal_id"))
    @Enumerated(EnumType.STRING)
    private Set<ChildNeeds> supportedNeeds;


    @ManyToOne
    @JoinColumn(name = "kitchen_id")
    private User kitchenStaff;

    @ManyToOne
    private DayRecord dayRecord;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MealConsumption> mealConsumptions;
}