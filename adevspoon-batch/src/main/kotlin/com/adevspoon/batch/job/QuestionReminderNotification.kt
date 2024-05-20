package com.adevspoon.batch.job

import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Configuration

// TODO: 추후 예정
@Configuration
class QuestionReminderNotification(
    private val entityManagerFactory: EntityManagerFactory
) {
    private val chunkSize = 500

    companion object {
        const val JOB_NAME = "questionReminderNotificationJob"
    }

}