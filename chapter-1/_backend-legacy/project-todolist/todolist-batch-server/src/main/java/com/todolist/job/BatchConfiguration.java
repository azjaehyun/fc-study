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

    // https://prodo-developer.tistory.com/167 - [SPRING] ItemWriterInterFace 구조 & CSV, JDBC , JPA데이터 읽기 실습

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    TodoListRepository TodoListRepository;

    @Bean
    public Job AdminJob() throws  Exception {
        return jobBuilderFactory.get("AdminJob")
                .incrementer(new RunIdIncrementer())
                .start(AdminStep())
                .build();
    }

    @Bean
    public Step AdminStep() throws  Exception{
        return stepBuilderFactory.get("AdminStep")
                .<TodoList, TodoList>chunk(10)
                .reader(AdminItemReader())
                .processor(AdminItemProcessor())
                .writer(AdminItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<TodoList> AdminItemReader() {
        return new JpaPagingItemReaderBuilder<TodoList>()
                .name("AdminItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM TodoList p")
                .pageSize(10)
                .build();
    }

    @Bean
    public TodoListItemProcessor AdminItemProcessor() {
        return new TodoListItemProcessor();
    }

    @Bean
    public JpaItemWriter<TodoList> AdminItemWriter() throws  Exception {
        JpaItemWriter<TodoList> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        List<TodoList> list = new ArrayList<TodoList>();
        for (int i = 10; i < 100; i++) {
            System.out.println("counting : "+i);
            list.add(TodoList.builder().id(Long.valueOf(i)).title("todolist-title-"+Integer.toString(i)).content("todolist-content-"+Integer.toString(i)).build());
        }
        TodoListRepository.saveAll(list);
        return writer;
    }
}
