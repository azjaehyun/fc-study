package com.todolist.domain.todolist;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class TodoList {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String content;
    private String regDtm;

    @Builder
    public TodoList(Long id, String title, String content , String regDtm){
        this.id = id;
        this.title = title;
        this.content = content;
         this.regDtm = regDtm;
    }
}
