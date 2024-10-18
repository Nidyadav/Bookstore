package com.example.bookstore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
          ScheduledBatchJob scheduledBatchJob=new ScheduledBatchJob(jobLauncher,csvImporterJob);
          scheduledBatchJob.performScheduledJob();

//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("startTime", System.currentTimeMillis())
//                    .toJobParameters();
//
//            jobLauncher.run(csvImporterJob, jobParameters);
//        } catch(RuntimeException e) {
//            log.error(e.getMessage());
//        }

    }
}
