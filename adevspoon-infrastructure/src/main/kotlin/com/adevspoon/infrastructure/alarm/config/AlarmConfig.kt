package com.adevspoon.infrastructure.alarm.config

import com.adevspoon.infrastructure.alarm.service.AlarmAdapter
import com.adevspoon.infrastructure.alarm.service.SlackAlarmAdapter
import com.slack.api.Slack
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AlarmConfig {
    @Bean
    @ConditionalOnProperty(prefix = "slack", name = ["webhook.url"])
    fun slackAlarmAdapter(slack: Slack): AlarmAdapter {
        return SlackAlarmAdapter(slack)
    }
}