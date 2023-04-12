package com.multi.domain.admin;

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
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String adminName;
    private String grant;
    @Builder
    public Admin(Long id, String adminName, String grant){
        this.adminName = adminName;
        this.grant = grant;
    }

}
