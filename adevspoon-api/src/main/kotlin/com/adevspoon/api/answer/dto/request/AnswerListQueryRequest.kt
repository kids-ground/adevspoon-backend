package com.adevspoon.api.answer.dto.request

import io.swagger.v3.oas.annotations.media.Schema


// TODO: Page 가능한 클래스 상속
data class AnswerListQueryRequest(
    @Schema(description = "게시물 타입 - 현", example = "ANSWER")
    val type: AnswerType = AnswerType.ANSWER,
    val sort: AnswerSortType = AnswerSortType.NEWEST,
    val questionId: Long? = null,
    val offset: Int = 0,
    val limit: Int = 10,
)