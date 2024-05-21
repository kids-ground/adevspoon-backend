package com.adevspoon.batch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor


@Configuration
class BatchTaskConfig {
    @Bean
    @Primary
    fun batchTaskExecutor(): TaskExecutor = ThreadPoolTaskExecutor()
        .apply {
            corePoolSize = 5
            maxPoolSize = 10
            setThreadNamePrefix("batch-task-")
            setWaitForTasksToCompleteOnShutdown(true)
            initialize()
        }
}