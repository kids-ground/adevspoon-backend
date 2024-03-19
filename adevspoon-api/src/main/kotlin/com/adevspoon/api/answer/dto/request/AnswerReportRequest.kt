package com.adevspoon.api.answer.dto.request

data class AnswerReportRequest(
    val type: PostType = PostType.ANSWER,
    val reason: PostReportType = PostReportType.NONE,
)