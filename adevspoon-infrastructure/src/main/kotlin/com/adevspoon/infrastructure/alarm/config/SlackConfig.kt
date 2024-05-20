package com.adevspoon.infrastructure.alarm.config

import com.slack.api.Slack
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackConfig {
    @Bean
    fun slackInstance(): Slack {
        return Slack.getInstance()
    }
}