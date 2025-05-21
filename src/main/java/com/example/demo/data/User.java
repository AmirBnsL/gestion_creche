package com.example.demo.data;

import com.example.demo.enums.ChildStatus;
import com.example.demo.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private ChildStatus childStatus;

    @OneToMany
    Collection<Attendance> attendances;

    @OneToMany
    Collection<Activity> activities;
}