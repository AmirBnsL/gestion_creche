package com.example.demo.repositories;

import com.example.demo.data.Child;
import com.example.demo.data.User;
import com.example.demo.enums.ChildStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findByStatus(ChildStatus status);
    List<Child> findByParent(User parent);

    @Query("SELECT c FROM Child c LEFT JOIN c.attendances a ON a.departureTime IS NULL WHERE a IS NULL OR a.departureTime IS NULL")
    List<Child> findChildrenWhoHaveNotDeparted();


    Optional<Child> findByIdAndParent(Long id, User parent);
}