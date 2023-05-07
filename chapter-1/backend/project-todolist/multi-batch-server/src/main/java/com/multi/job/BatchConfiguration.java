package com.multi.job;

import com.multi.domain.admin.AdminUser;
import com.multi.impl.AdminUserRepository;
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
    AdminUserRepository adminUserRepository;

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
                .<AdminUser, AdminUser>chunk(10)
                .reader(AdminItemReader())
                .processor(AdminItemProcessor())
                .writer(AdminItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<AdminUser> AdminItemReader() {
        return new JpaPagingItemReaderBuilder<AdminUser>()
                .name("AdminItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM AdminUser p")
                .pageSize(10)
                .build();
    }

    @Bean
    public AdminItemProcessor AdminItemProcessor() {
        return new AdminItemProcessor();
    }

    @Bean
    public JpaItemWriter<AdminUser> AdminItemWriter() throws  Exception {
        JpaItemWriter<AdminUser> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        List<AdminUser> list = new ArrayList<AdminUser>();
        for (int i = 10; i < 100; i++) {
            System.out.println("counting : "+i);
            list.add(AdminUser.builder().id(Long.valueOf(i)).adminName("adminName-"+Integer.toString(i)).grant("grantSample").build());
        }
        adminUserRepository.saveAll(list);
        return writer;
    }
}
