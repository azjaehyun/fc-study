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
    // 필드를 추가 수정

    @Builder
    public TodoList(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
