package com.adevspoon.infrastructure.notification.service

import com.adevspoon.infrastructure.notification.dto.GroupNotificationInfo
import com.adevspoon.infrastructure.notification.dto.NotificationInfo
import com.adevspoon.infrastructure.notification.dto.NotificationResponse
import com.adevspoon.infrastructure.notification.dto.NotificationType
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.slf4j.LoggerFactory


class FCMNotificationAdapter(
    private val messaging: FirebaseMessaging
): PushNotificationAdapter {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun sendMessageSync(info: NotificationInfo) = sendMessageSync(info.toGroupNotificationInfo())

    override fun sendMessageSync(info: GroupNotificationInfo) =
        retry(3, info) { notificationInfo ->
            val messages = notificationInfo.tokens.map { getMessage(notificationInfo.type, it, notificationInfo.data) }
            val batchSendResponse = sendEachSync(messages)
            batchSendResponse.toNotificationResponse(notificationInfo.tokens)
        }

    private fun retry(
        count: Int,
        initInfo : GroupNotificationInfo,
        execute: (GroupNotificationInfo) -> NotificationResponse
    ): NotificationResponse {
        var notificationInfo = initInfo

        for (i in 0 until count) {
            val executeResult = execute(notificationInfo)
            notificationInfo = GroupNotificationInfo(
                notificationInfo.type,
                executeResult.failureTokens
            )

            if (executeResult.failure == 0) break
        }

        log.info("FCM Send - 성공: ${initInfo.tokens.size - notificationInfo.tokens.size}, 실패: ${notificationInfo.tokens.size}")

        return NotificationResponse(
            initInfo.tokens.size - notificationInfo.tokens.size,
            notificationInfo.tokens.size,
            notificationInfo.tokens
        )
    }

    private fun getMessage(type: NotificationType, token: String, data: Map<String, String>? = null): Message {
        val notification = Notification.builder()
            .setTitle(type.title)
            .setBody(type.body)
            .build()
        return Message.builder()
            .setNotification(notification)
            .setToken(token)
            .putAllData(data ?: emptyMap<String, String>())
            .build()
    }

    private fun sendEachSync(messages: List<Message>): BatchSendResponse {
        try {
            val batchResponse = messaging.sendEach(messages)
            val failedMessageIndexes = batchResponse.responses
                .withIndex()
                .filter { (idx, v) -> !v.isSuccessful }
                .map { (idx, v) -> idx }

            return BatchSendResponse(
                batchResponse.successCount,
                batchResponse.failureCount,
                failedMessageIndexes
            )
        } catch (e: FirebaseMessagingException) {
            throw e
        }
    }

    data class BatchSendResponse(
        val success: Int,
        val failure: Int,
        val failedMessageIndexes: List<Int>,
    ) {
        fun toNotificationResponse(tokens: List<String>) =
            NotificationResponse(
                success,
                failure,
                failedMessageIndexes.map { tokens[it] }
            )
    }
}