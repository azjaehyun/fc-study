package com.multi.domain.recruit;

import com.multi.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class Recruit {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long adminId;
    private Long userId;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private Set<User> wedulStudentList = new LinkedHashSet<>();

    private String regDt;
    @Builder
    public Recruit(Long id, Long adminId,Long userId, String regDt){
        this.id = id;
        this.adminId = adminId;
        this.userId = userId;
        this.regDt = regDt;
    }

}
