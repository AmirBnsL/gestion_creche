package com.example.demo.controllers;

import com.example.demo.services.ChildService;
import com.example.demo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final ChildService childService;
    private final UserService userService;

    @GetMapping("/home")
    public String adminHome(Model model) {
        model.addAttribute("approvedChildren", childService.getApprovedChildren());
        return "admin/admin-home";
    }

    @GetMapping("/pending")
    public String viewPending(Model model) {
        model.addAttribute("children", childService.getPendingChildren());
        return "admin/pending_children";
    }


    @GetMapping("/user/pending")
    public String viewPendingAccounts(Model model) {
        model.addAttribute("accounts", userService.getPendingUsers());
        return "admin/pending_accounts";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        childService.approveChild(id);
        return "redirect:/admin/pending";
    }

    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        childService.rejectChild(id);
        return "redirect:/admin/pending";
    }

    @PostMapping("/account/approve/{id}")
    public String approveAccount(@PathVariable Long id) {
        userService.approveAccount(id);
        return "redirect:/admin/user/pending";
    }

    @PostMapping("/account/reject/{id}")
    public String rejectAccount(@PathVariable Long id) {
        userService.rejectAccount(id);
        return "redirect:/admin/user/pending";
    }

}
