package com.todolist.job;

import com.todolist.domain.todolist.TodoList;
import com.todolist.impl.TodoListRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    TodoListRepository TodoListRepository;

    @Bean
    public Job TodoJob() throws  Exception {
        return jobBuilderFactory.get("TodoJob")
                .incrementer(new RunIdIncrementer())
                .start(TodoStep())
                .build();
    }

    @Bean
    public Step TodoStep() throws  Exception{
        return stepBuilderFactory.get("TodoStep")
                .<TodoList, TodoList>chunk(10)
                .reader(TodoListItemReader())
                .processor(TodoListItemProcessor())
                .writer(TodoListItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<TodoList> TodoListItemReader() {
        return new JpaPagingItemReaderBuilder<TodoList>()
                .name("TodoListItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM TodoList p")
                .pageSize(10)
                .build();
    }

    @Bean
    public TodoListItemProcessor TodoListItemProcessor() {
        return new TodoListItemProcessor();
    }

    @Bean
    public JpaItemWriter<TodoList> TodoListItemWriter() throws  Exception {
        JpaItemWriter<TodoList> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        List<TodoList> list = new ArrayList<TodoList>();

        // 반복되는 작업을 구현 ex) sms , email 발송
        for (int i = 10; i < 100; i++) {
            System.out.println("counting : "+i);
            list.add(TodoList.builder()
                  .id(Long.valueOf(i))
                  .title("todolist-title-"+Integer.toString(i))
                  .content("todolist-content-"+Integer.toString(i)).build());
        }
        
        TodoListRepository.saveAll(list);
        return writer;
    }
}
