package com.adevspoon.domain.common.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityEvent(
    val type: ActivityEventType
)