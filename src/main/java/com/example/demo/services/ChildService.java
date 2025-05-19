package com.example.demo.services;

import com.example.demo.data.Child;
import com.example.demo.data.User;
import com.example.demo.enums.ChildStatus;
import com.example.demo.repositories.ChildRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;
    private final UserService userService;

    public void saveChild(Child child, String username) {
        User parent = userService.findByUsername(username);
        child.setParent(parent);
        child.setStatus(ChildStatus.PENDING);
        childRepository.save(child);
    }

    public List<Child> getChildrenForParent(String username) {
        return childRepository.findByParent(userService.findByUsername(username));
    }

    public List<Child> getPendingChildren() {
        return childRepository.findByStatus(ChildStatus.PENDING);
    }

    public void approveChild(Long id) {
        Child c = childRepository.findById(id).orElseThrow();
        c.setStatus(ChildStatus.VALIDATED);
        childRepository.save(c);
    }

    public void rejectChild(Long id) {
        Child c = childRepository.findById(id).orElseThrow();
        c.setStatus(ChildStatus.REJECTED);
        childRepository.save(c);
    }

    public List<Child> getApprovedChildren() {
        return childRepository.findByStatus(ChildStatus.VALIDATED);
    }
}