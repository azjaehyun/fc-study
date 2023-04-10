package com.multi.controller;

import com.multi.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ServiceController {

    @GetMapping("/test")
    public Member getMemer(){
        Member member = Member.builder()
                .id(1L)
                .name("jaehyun")
                .age(39)
                .build();
        return member;
    }
}
