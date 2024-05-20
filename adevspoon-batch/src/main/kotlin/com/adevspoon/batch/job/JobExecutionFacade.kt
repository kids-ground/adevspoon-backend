package com.adevspoon.batch.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher
import org.springframework.context.ApplicationContext
import org.springframework.core.task.TaskExecutor
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class JobExecutionFacade(
    private val jobLauncher: JobLauncher,
    private val batchTaskExecutor: TaskExecutor,
    private val applicationContext: ApplicationContext,
) {
    fun executeJob() {
        (jobLauncher as TaskExecutorJobLauncher).setTaskExecutor(batchTaskExecutor)

        // TODO: 추후 Event Type에 따라 Job, JobParameter 나누기
        val jobParameters = JobParametersBuilder()
            .addString("date", LocalDateTime.now().toString())
            .toJobParameters()
        val job = applicationContext.getBean(QuestionPublishedNotification.JOB_NAME, Job::class.java)
        jobLauncher.run(job, jobParameters)
    }
}