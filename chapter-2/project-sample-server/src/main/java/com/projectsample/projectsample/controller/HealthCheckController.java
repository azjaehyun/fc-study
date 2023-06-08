package com.projectsample.projectsample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck(@RequestParam(value = "name", defaultValue = "OK") String name) {
        return String.format("service health Check %s!", name);
    }
}
