package com.example.demo.data;
import com.example.demo.enums.ChildStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Child {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String birthDate;
    private String address;
    private String allergies;
    private String specialNeeds;

    @ManyToOne
    private User parent;

    private ChildStatus status;

    @OneToMany
    Collection<Attendance> attendances;

    @OneToMany
    Collection<DevelopmentRecord> developmentRecords;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MealConsumption> mealConsumptions;

    @ManyToMany(mappedBy = "participants")
    private Set<Activity> activities;

}