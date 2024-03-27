package com.adevspoon.api.question.dto.request

import io.swagger.v3.oas.annotations.media.Schema


// TODO: Enum Validate, Converter 필요
data class QuestionListRequest(
    val sort: QuestionSortType = QuestionSortType.NEWEST,
    @Schema(description = "등록한 답변만 가져올것인지 여부")
    val isAnswered: Boolean = false,
    val offset: Int = 0,
    val limit: Int = 10,
)