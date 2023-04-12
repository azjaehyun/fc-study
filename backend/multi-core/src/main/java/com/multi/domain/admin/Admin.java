package com.multi.domain.admin;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class Admin {


    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String adminName;
    private String grant;
    @Builder
    public Admin(Long id, String adminName, String grant){
        this.id = id;
        this.adminName = adminName;
        this.grant = grant;
    }

}
