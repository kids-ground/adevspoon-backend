package com.adevspoon.infrastructure.notification.dto

data class NotificationInfo(
    val type: NotificationType,
    val token: String,
    val data: Map<String, String>? = null
) {
    fun toGroupNotificationInfo(): GroupNotificationInfo {
        return GroupNotificationInfo(type, listOf(token), data)
    }
}
