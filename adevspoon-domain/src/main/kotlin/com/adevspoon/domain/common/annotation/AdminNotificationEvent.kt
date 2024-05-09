package com.adevspoon.domain.common.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AdminNotificationEvent(
    val type: AdminMessageType
)
