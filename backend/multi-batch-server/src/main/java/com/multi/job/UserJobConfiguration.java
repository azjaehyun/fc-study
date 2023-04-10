package com.multi.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class UserJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job userJob(){
        return jobBuilderFactory.get("userJob").start(userStep()).build();
    }

    @Bean
    public Step userStep() {
        return stepBuilderFactory.get("userStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is userStep");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
