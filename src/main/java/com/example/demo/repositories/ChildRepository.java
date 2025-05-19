package com.example.demo.repositories;

import com.example.demo.data.Child;
import com.example.demo.data.User;
import com.example.demo.enums.ChildStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findByStatus(ChildStatus status);
    List<Child> findByParent(User parent);
}