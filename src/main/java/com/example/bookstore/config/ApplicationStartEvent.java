package com.example.bookstore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationStartEvent {
    private final JobLauncher jobLauncher;
    private final Job csvImporterJob;

    @Autowired
    public ApplicationStartEvent(JobLauncher jobLauncher, Job csvImporterJob) {
        this.jobLauncher = jobLauncher;
        this.csvImporterJob = csvImporterJob;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReadyEvent() throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startTime",System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(csvImporterJob, jobParameters);
    }
}
