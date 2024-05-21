package com.adevspoon.infrastructure.notification.dto

data class NotificationResponse(
    val success: Int,
    val failure: Int,
    val failureTokens: List<String>,
)
