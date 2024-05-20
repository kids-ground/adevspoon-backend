package com.adevspoon.infrastructure.alarm.service

import com.adevspoon.infrastructure.alarm.dto.AlarmType
import com.slack.api.Slack
import com.slack.api.model.Attachment
import com.slack.api.model.Field
import com.slack.api.webhook.WebhookPayloads
import org.springframework.beans.factory.annotation.Value

class SlackAlarmAdapter(
    private val slackInstance: Slack
): AlarmAdapter {
    @Value("\${slack.webhook.url}")
    private lateinit var slackWebhookUrl: String

    override fun sendAlarm(type: AlarmType, info: Map<String, Any>) {
        val attachment = generateAttachment(info)

        slackInstance.send(slackWebhookUrl, WebhookPayloads.payload { p ->
            p.text("*${type.title}*")
                .attachments(listOf(attachment))
        })
    }

    private fun generateAttachment(info: Map<String, Any>): Attachment {
        return Attachment.builder()
            .color("fa8128")
            .fields(
                info.entries.map { (title, value) ->
                    generateField(title, value.toString())
                }
            )
            .build()
    }

    private fun generateField(title: String, value: String): Field {
        return Field.builder()
            .title(title)
            .value(value)
            .valueShortEnough(false)
            .build()
    }
}