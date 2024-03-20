package com.adevspoon.api.answer.dto.request

data class AnswerReportRequest(
    val type: AnswerType = AnswerType.ANSWER,
    val reason: PostReportType = PostReportType.NONE,
)