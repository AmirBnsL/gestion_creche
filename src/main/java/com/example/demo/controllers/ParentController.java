package com.example.demo.controllers;

import com.example.demo.data.Child;
import com.example.demo.data.DevelopmentRecord;
import com.example.demo.services.AttendanceService;
import com.example.demo.services.ChildService;
import com.example.demo.services.EducatorService;
import com.example.demo.services.MealService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/parent")
@AllArgsConstructor
public class ParentController {

    private final ChildService childService;
    private final MealService mealService;
    private final AttendanceService attendanceService;
    private final EducatorService educatorService;

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

    @GetMapping("/child/{id}")
    public String childDetails(@PathVariable Long id, Principal principal, Model model) {
        Child child = childService.getChildByIdAndParentUsername(id, principal.getName());
        List<DevelopmentRecord> developmentRecords = educatorService.getRecordsForChild(child.getId());

        model.addAttribute("child", child);
        model.addAttribute("attendance", attendanceService.getTodayAttendanceForChild(child));
        model.addAttribute("mealConsumptions", mealService.getTodayMealConsumptionsForChild(child));
        model.addAttribute("activities", mealService.getTodayActivitiesForChild(child));
        model.addAttribute("developments", developmentRecords);
        return "parent/child_details";
    }
}
