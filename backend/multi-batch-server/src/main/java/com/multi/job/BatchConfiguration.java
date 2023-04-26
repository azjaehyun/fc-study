package com.multi.job;

import com.multi.domain.admin.Admin;
import com.multi.impl.AdminRepository;
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
    AdminRepository adminRepository;

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
                .<Admin, Admin>chunk(10)
                .reader(AdminItemReader())
                .processor(AdminItemProcessor())
                .writer(AdminItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Admin> AdminItemReader() {
        return new JpaPagingItemReaderBuilder<Admin>()
                .name("AdminItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM Admin p")
                .pageSize(10)
                .build();
    }

    @Bean
    public AdminItemProcessor AdminItemProcessor() {
        return new AdminItemProcessor();
    }

    @Bean
    public JpaItemWriter<Admin> AdminItemWriter() throws  Exception {
        JpaItemWriter<Admin> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        List<Admin> list = new ArrayList<Admin>();
        for (int i = 10; i < 100; i++) {
            System.out.println("counting : "+i);
            list.add(Admin.builder().id(Long.valueOf(i)).adminName("adminName-"+Integer.toString(i)).grant("grantSample").build());
        }
        adminRepository.saveAll(list);
        return writer;
    }
}
