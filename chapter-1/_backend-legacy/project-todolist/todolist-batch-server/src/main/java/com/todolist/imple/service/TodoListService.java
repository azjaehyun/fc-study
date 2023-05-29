package com.todolist.imple.service;

import com.todolist.domain.todolist.TodoList;
import com.todolist.impl.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TodoListService {
    @Autowired
    TodoListRepository todoListRepository;

    public List<TodoList> getAllSamples() {
        return todoListRepository.findAll();
    }

    public TodoList createSample(TodoList sample) {
        return todoListRepository.save(sample);
    }

    public List<TodoList> createSamples(List<TodoList> sample) {
        return todoListRepository.saveAll(sample);
    }

    public TodoList getSampleById(Long id) {
        return todoListRepository.findById(id).orElse(null);
    }

    public void deleteSampleById(Long id) {
        todoListRepository.deleteById(id);
    }
}
