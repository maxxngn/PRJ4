package com.example.fashion2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AdminController {
    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("content", "admin/dashboard"); // Reference to actual content fragment
        return "layout/adminLayout"; // This should map to layout/adminLayout.html
    }
}
