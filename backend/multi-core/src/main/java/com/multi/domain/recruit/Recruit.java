package com.multi.domain.recruit;

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
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long adminId;
    private Long userId;
    private String regDt;
    @Builder
    public Recruit(Long id, Long adminId,Long userId, String regDt){
        this.id = id;
        this.adminId = adminId;
        this.userId = userId;
        this.regDt = regDt;
    }

}
