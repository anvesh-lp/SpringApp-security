package com.anvesh.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String loginPage() {
        System.out.println("In login page");
        return "login";
    }

    @GetMapping("courses")
    public String getcourse() {
        System.out.println("In courses page");
        return "courses";
    }
}
