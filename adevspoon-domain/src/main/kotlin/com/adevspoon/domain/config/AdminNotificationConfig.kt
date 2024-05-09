package com.adevspoon.domain.config

import com.adevspoon.domain.common.service.AdminNotificationService
import com.adevspoon.domain.common.service.SlackNotificationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AdminNotificationConfig {

    @Bean
    fun notificationService(): AdminNotificationService {
        return SlackNotificationService()
    }
}