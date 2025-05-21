package com.example.demo.configs;

import com.example.demo.data.Child;
import com.example.demo.data.User;
import com.example.demo.enums.ChildStatus;
import com.example.demo.enums.Role;
import com.example.demo.repositories.ChildRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, ChildRepository childRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create users for each actor
            User kitchen = new User();
            kitchen.setUsername("kitchen");
            kitchen.setEmail("kitchen@gmail.com");
            kitchen.setPassword(passwordEncoder.encode("password"));
            kitchen.setRole(Role.KITCHEN_STAFF);
            kitchen.setChildStatus(ChildStatus.VALIDATED);
            userRepository.save(kitchen);

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole(Role.ADMIN);
            admin.setChildStatus(ChildStatus.VALIDATED);
            userRepository.save(admin);

            User educator = new User();
            educator.setUsername("educator");
            educator.setEmail("educator@gmail.com");
            educator.setPassword(passwordEncoder.encode("password"));
            educator.setRole(Role.EDUCATOR);
            educator.setChildStatus(ChildStatus.VALIDATED);
            userRepository.save(educator);

            User parent = new User();
            parent.setUsername("parent");
            parent.setEmail("parent@gmail.com");
            parent.setPassword(passwordEncoder.encode("password"));
            parent.setRole(Role.PARENT);
            parent.setChildStatus(ChildStatus.VALIDATED);
            userRepository.save(parent);

            // Create children for the parent
            Child amir = new Child();
            amir.setFirstName("Amir");
            amir.setLastName("ParentLastName");
            amir.setParent(parent);
            childRepository.save(amir);

            Child barae = new Child();
            barae.setFirstName("Barae");
            barae.setLastName("ParentLastName");
            barae.setParent(parent);
            childRepository.save(barae);

            // Save parent with children

            userRepository.save(parent);

            System.out.println("Data initialized successfully.");
        };
    }
}