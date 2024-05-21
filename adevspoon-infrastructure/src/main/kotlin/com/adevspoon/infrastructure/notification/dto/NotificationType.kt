package com.adevspoon.infrastructure.notification.dto

enum class NotificationType(
    val title: String,
    val body: String
) {
    QUESTION_OPENED(
        title = "띵동~ 오늘의 문제 도착📮",
        body = "오늘의 문제 풀고 실력 향상 쑥쑥!"
    ),
    QUESTION_REMINDER(
        title = "성장을 위해 조금만 힘내보아요🔥",
        body = "문제 풀고 오늘도 한 발짝 더 나아가요!"
    ),
}