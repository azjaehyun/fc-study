package com.multi.domain.admin;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class AdminUser {


    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String adminName;
    @Builder
    public AdminUser(Long id, String adminName, String grant){
        this.id = id;
        this.adminName = adminName;
    }

}
