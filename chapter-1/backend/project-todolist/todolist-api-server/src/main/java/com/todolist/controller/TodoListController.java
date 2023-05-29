package com.todolist.controller;

import com.todolist.domain.todolist.TodoList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TodoListController {

    @GetMapping("/todo-list")
    public TodoList getMemer(){
        TodoList user = TodoList.builder()
                .id(1L)
                .title("jaehyun-trigger-final")
                .content("todlistContent..")
                .build();
        return user;
    }
}
