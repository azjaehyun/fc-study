package com.multi.job;

import com.multi.domain.admin.Admin;
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

//        JpaItemWriter<Admin> writer = new JpaItemWriterBuilder<Admin>()
//                .entityManagerFactory(this.entityManagerFactory)
//                .usePersist(true)
//                .build();
//
//        writer.afterPropertiesSet();
//        List<Admin> items = new ArrayList<>();
//        for (int i = 10; i < 100; i++) {
//            System.out.println("counting : "+Long.valueOf(i));
//            items.add(new Admin(Long.valueOf(i), "foo" ,"tt"));
//        }
        return writer;
    }
}