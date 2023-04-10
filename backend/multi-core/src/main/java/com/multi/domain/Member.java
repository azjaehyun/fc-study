package com.multi.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    private Long id;
    private String name;
    private int age;


    @Builder
    public Member(Long id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
