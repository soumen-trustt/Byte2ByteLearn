package com.soumenprogramming.byte2byte.learn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StaticPagesController {

    @GetMapping("/about")
    public String about() {
        return "forward:/about.html";
    }

    @GetMapping("/contact")
    public String contact() {
        return "forward:/contact.html";
    }

    @PostMapping("/contact")
    public String handleContactForm() {
        // TODO: Add logic to handle form submission (e.g., send email, save to database)
        return "redirect:/contact?success";
    }
    
    @GetMapping("/courses")
    public String courses() {
        return "forward:/courses.html";
    }

    @GetMapping("/course/java-basics")
    public String javaBasics() {
        return "redirect:https://medium.com/@mail2soumen007/java-programming-basics-1e4845feddb6";
    }
}
