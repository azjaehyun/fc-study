package com.projectsample.projectsample.controller;

import com.projectsample.projectsample.domain.SampleProject;
import com.projectsample.projectsample.imple.service.SampleProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HealthCheckController {

    @Autowired
    SampleProjectService sampleProjectService;

    @GetMapping("/health")
    public String healthCheck(@RequestParam(value = "name", defaultValue = "OK") String name) {
        return String.format("service health Check %s!", name);
    }

    @GetMapping("/api/getData")
    public List<SampleProject> getData() {
        List<SampleProject> list = sampleProjectService.getAllSamples();
        return list ;
    }


}
