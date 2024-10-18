package com.example.bookstore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledBatchJob {
    private final JobLauncher jobLauncher;
    private final Job csvImporterJob;
    @Autowired
    public ScheduledBatchJob(JobLauncher jobLauncher, Job csvImporterJob) {
        this.jobLauncher = jobLauncher;
        this.csvImporterJob = csvImporterJob;
    }
    @Scheduled(cron = "0 0 * * * *")
    public  void performScheduledJob(){
      try{
          JobParameters jobParameters=new JobParametersBuilder()
                  .addLong("start time",System.currentTimeMillis())
                  .toJobParameters();
          jobLauncher.run(csvImporterJob,jobParameters);
          log.info("Cron job running at"+System.currentTimeMillis());
      } catch (Exception e) {
          log.error(e.getMessage());
      }
    }
}
