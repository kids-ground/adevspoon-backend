package com.adevspoon.api.answer.dto.request

import io.swagger.v3.oas.annotations.media.Schema

// TODO: enum validation 필요
data class AnswerReportRequest(
    @Schema(description = "무조건 answer", example = "answer", nullable = true, defaultValue = "answer")
    val type: AnswerType = AnswerType.ANSWER,

    @Schema(description = "신고 사유", example = "none", nullable = true, defaultValue = "none")
    val reason: PostReportType = PostReportType.NONE,
)