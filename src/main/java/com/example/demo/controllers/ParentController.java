package com.example.demo.controllers;

import com.example.demo.data.Child;
import com.example.demo.services.ChildService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/parent")
@AllArgsConstructor
public class ParentController {

    private final ChildService childService;

    @GetMapping("/home")
    public String parentHome() {
        return "parent/parent-home";
    }

    @GetMapping("/child/register")
    public String showChildForm(Model model) {
        model.addAttribute("child", new Child());
        return "parent/register_child";
    }

    @PostMapping("/child/register")
    public String registerChild(@ModelAttribute Child child, Principal principal) {
        childService.saveChild(child, principal.getName());
        return "redirect:/parent/home";
    }

    @GetMapping("/children")
    public String viewChildren(Model model, Principal principal) {
        model.addAttribute("children", childService.getChildrenForParent(principal.getName()));
        return "parent/child_list";
    }
}
