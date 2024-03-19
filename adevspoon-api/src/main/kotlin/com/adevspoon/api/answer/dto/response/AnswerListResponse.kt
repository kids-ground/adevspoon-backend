package com.adevspoon.api.answer.dto.response

data class AnswerListResponse(
    val list: AnswerInfoResponse,
    val next: String?
)
