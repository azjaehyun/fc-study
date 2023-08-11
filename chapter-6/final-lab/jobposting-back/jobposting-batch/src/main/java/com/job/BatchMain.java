package com.job;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@EnableBatchProcessing
@SpringBootApplication
public class BatchMain {
    public static void main(String[] args) {
        System.out.println("배치 서버에요.");
        SpringApplication app = new SpringApplication(BatchMain.class);
    }
}
