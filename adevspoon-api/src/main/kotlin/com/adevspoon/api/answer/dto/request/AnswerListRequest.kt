package com.adevspoon.api.answer.dto.request

data class AnswerListRequest(
    // TODO: Page 가능한 클래스 상속
    val type: PostType = PostType.ANSWER,
    val sort: AnswerSortType = AnswerSortType.NEWEST,
    val questionId: Long? = null,
    val offset: Int = 0,
    val limit: Int = 10,
)