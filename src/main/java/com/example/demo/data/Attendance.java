package com.example.demo.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Child child;

    private LocalTime arrivalTime;

    private LocalTime departureTime;

    @ManyToOne private DayRecord dayRecord;

    @ManyToOne
    private User educator;
    // getters/setters
}