package com.adevspoon.infrastructure.notification.dto

data class GroupNotificationInfo(
    val type: NotificationType,
    val tokens: List<String>,
    val data: Map<String, String>? = null
)