package com.adevspoon.domain.common.service

import com.adevspoon.domain.common.event.ReportEvent
import com.slack.api.Slack
import com.slack.api.model.Attachment
import com.slack.api.model.Field
import com.slack.api.webhook.WebhookPayloads
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SlackNotificationService: AdminNotificationService{

    @Value("\${slack.webhook.url}")
    private lateinit var SLACK_WEBHOOK_URL: String
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun sendReportNotification(event: ReportEvent) {
        val slack = Slack.getInstance()
        try {
            slack.send(SLACK_WEBHOOK_URL, WebhookPayloads.payload { p ->
                p.text("*신고 접수* :warning:")
                    .attachments(listOf(generateSlackAttachment(event)))
            })
        } catch (e: IOException) {
            logger.warn("IOException occurred when sending message to Slack", e)
        }
    }

    private fun generateSlackAttachment(event: ReportEvent): Attachment {
        val requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())
        val contentId = event.report.getContentId()
        return Attachment.builder()
            .color("fa8128")
            .fields(listOf(
                generateSlackField("Report Time", requestTime),
                generateSlackField("Report reason", event.report.reason.toString()),
                generateSlackField("Report Type", event.report.postType),
                generateSlackField("Report (id: $contentId)", event.content),
                generateSlackField("Reported by", "id: ${event.report.user.id}, name: ${event.report.user.nickname}")))
            .build()
    }

    private fun generateSlackField(title: String, value: String): Field {
        return Field.builder()
            .title(title)
            .value(value)
            .valueShortEnough(false)
            .build()
    }
}