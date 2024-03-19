package com.adevspoon.api.question.dto.request


data class QuestionListRequest(
    // TODO: Pageable 객체로 변경
    val sort: QuestionSortType = QuestionSortType.NEWEST,
    val isAnswered: Boolean = false,
    val offset: Int = 0,
    val limit: Int = 10,
)