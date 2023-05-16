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
    private Long id;
    private String title;
    private String content;

    @Builder
    public TodoList(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
