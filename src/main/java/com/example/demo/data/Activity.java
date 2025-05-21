package com.example.demo.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne private DayRecord dayRecord;


    @ManyToOne
    private User educator;

    @ManyToMany
    @JoinTable(
            name = "participants",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id"))
    private Set<Child> participants;

    @PrePersist
    @PreUpdate
    private void validateTimes() {

            if (!startTime.isBefore(endTime)) {
                throw new IllegalArgumentException("Start time must be before end time.");
            }
    }
    // getters/setters
}
