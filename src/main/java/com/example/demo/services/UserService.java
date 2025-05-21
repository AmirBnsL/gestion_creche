package com.example.demo.services;

import com.example.demo.data.User;
import com.example.demo.enums.ChildStatus;
import com.example.demo.enums.Role;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(User user) {

        //check if the user already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setChildStatus(user.getRole()== Role.ADMIN ? ChildStatus.VALIDATED : ChildStatus.PENDING);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getPendingUsers() {
        return userRepository.findUsersByChildStatusIn(List.of(ChildStatus.PENDING, ChildStatus.REJECTED));
    }

    public void approveAccount(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setChildStatus(ChildStatus.VALIDATED);
        userRepository.save(user);
    }

    public void rejectAccount(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setChildStatus(ChildStatus.REJECTED);
        userRepository.save(user);
    }
}