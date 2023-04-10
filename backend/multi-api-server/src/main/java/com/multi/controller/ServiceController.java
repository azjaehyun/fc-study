package com.multi.controller;

import com.multi.domain.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ServiceController {

    @GetMapping("/test")
    public User getMemer(){
        User user = User.builder()
                .id(1L)
                .name("jaehyun")
                .age(39)
                .build();
        return user;
    }
}
