package com.example.demo.repositories;

import com.example.demo.data.User;
import com.example.demo.enums.ChildStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);


    List<User> findUsersByChildStatusIn(Collection<ChildStatus> childStatuses);
}