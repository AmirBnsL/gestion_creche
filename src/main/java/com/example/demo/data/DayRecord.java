package com.example.demo.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class DayRecord {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private LocalDate date;

    @OneToMany(mappedBy = "dayRecord") private List<Activity> activities;
    @OneToMany(mappedBy = "dayRecord") private List<Meal> meals;
    @OneToMany(mappedBy = "dayRecord") private List<Attendance> attendances;
}