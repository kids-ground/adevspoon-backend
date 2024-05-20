package com.adevspoon.batch.job

import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.infrastructure.alarm.dto.AlarmType
import com.adevspoon.infrastructure.alarm.service.AlarmAdapter
import com.adevspoon.infrastructure.notification.dto.GroupNotificationInfo
import com.adevspoon.infrastructure.notification.dto.NotificationType
import com.adevspoon.infrastructure.notification.service.PushNotificationAdapter
import jakarta.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class QuestionPublishedNotification(
    private val transactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val pushNotificationAdapter: PushNotificationAdapter,
    private val alarmAdapter: AlarmAdapter,
){
    private val logger = LoggerFactory.getLogger(this.javaClass)!!
    private val chunkSize = 500
    private val successCountKey = "successCount"
    private val failCountKey = "failCount"

    companion object {
        const val JOB_NAME = "질문발급_푸시알림"
    }

    @Bean(JOB_NAME)
    fun job(jobRepository: JobRepository): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .preventRestart()
            .start(notificationStep(jobRepository))
            .listener(jobListener())
            .build()
    }

    // 멀티쓰레드 처리
    @Bean(JOB_NAME + "_step")
    @JobScope
    fun notificationStep(jobRepository: JobRepository): Step {
        return StepBuilder(JOB_NAME + "_step", jobRepository)
            .chunk<UserEntity, UserEntity>(chunkSize, transactionManager)
            .reader(reader())
            .writer(pushWriter(null))
            .taskExecutor(executor())
            .build()
    }

    @Bean(JOB_NAME + "_taskPool")
    fun executor(): TaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 5
        executor.setThreadNamePrefix("task-step-")
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.initialize()
        return executor
    }

    @Bean(JOB_NAME + "_reader")
    @StepScope
    fun reader(): JpaPagingItemReader<UserEntity> {
        return JpaPagingItemReaderBuilder<UserEntity>()
            .name(JOB_NAME + "_reader")
            .queryString("SELECT u FROM UserEntity u WHERE u.fcmToken IS NOT NULL")
            .pageSize(chunkSize)
            .entityManagerFactory(entityManagerFactory)
            .saveState(false)
            .build()
    }

    @Bean
    @StepScope
    fun pushWriter(@Value("#{stepExecution.jobExecution.executionContext}") jobExecutionContext: ExecutionContext?): ItemWriter<UserEntity> {
        return ItemWriter {
            val notificationResponse = pushNotificationAdapter.sendMessageSync(
                GroupNotificationInfo(
                    NotificationType.QUESTION_OPENED,
                    it.items.map { user -> user.fcmToken!! }
                )
            )

            jobExecutionContext?.let {
                synchronized(jobExecutionContext) {
                    val successCount = jobExecutionContext.getInt(successCountKey, 0)
                    val failCount = jobExecutionContext.getInt(failCountKey, 0)
                    jobExecutionContext.put(successCountKey, successCount + notificationResponse.success)
                    jobExecutionContext.put(failCountKey, failCount + notificationResponse.failure)
                }
            }
        }
    }

    @Bean(JOB_NAME + "_listener")
    fun jobListener(): JobExecutionListener {
        return object : JobExecutionListener {
            override fun afterJob(jobExecution: JobExecution) {
                val pushSuccess = jobExecution.executionContext.getInt("successCount", -1)
                val pushFail = jobExecution.executionContext.getInt("failCount", -1)
                val jobInfo = mapOf<String, Any>(
                    "Batch Name" to JOB_NAME,
                    "Push Success" to pushSuccess,
                    "Push Fail" to pushFail,
                )

                if (jobExecution.status == BatchStatus.COMPLETED) {
                    logger.info("Push Finished - 성공: $pushSuccess, 실패: $pushFail")
                    alarmAdapter.sendAlarm(AlarmType.BATCH_COMPLETE, jobInfo)
                } else {
                    logger.error("Push Failed - 성공: $pushSuccess, 실패: $pushFail")
                    alarmAdapter.sendAlarm(AlarmType.BATCH_ERROR, jobInfo)
                }
            }
        }
    }

}