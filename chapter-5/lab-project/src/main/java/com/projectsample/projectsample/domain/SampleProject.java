package com.projectsample.projectsample.domain;

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
public class SampleProject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String projectName;
    private String description;

    @Builder
    public SampleProject(Long id, String title, String content){
        this.id = id;
        this.projectName = title;
        this.description = content;
    }
}
