package com.example.thymeleaftour.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by rajeevkumarsingh on 02/07/17.
 */
@Controller
public class HomeController {

    private static final String appName = "Chapter-5 Sample";

    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(value = "name", required = false,
                               defaultValue = "Fast Campus Member") String name) {

        model.addAttribute("name", name);
        model.addAttribute("title", appName);
        return "home";

    }
}
