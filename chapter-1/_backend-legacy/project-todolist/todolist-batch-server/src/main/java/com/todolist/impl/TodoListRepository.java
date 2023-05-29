package com.todolist.impl;

import com.todolist.domain.todolist.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<TodoList,Long> {

}
