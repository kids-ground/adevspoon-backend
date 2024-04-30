package com.adevspoon.domain.techQuestion.dto.request

import com.adevspoon.domain.techQuestion.dto.enums.IssuedQuestionSortType

data class GetIssuedQuestionList(
    val memberId: Long,
    val sort: IssuedQuestionSortType = IssuedQuestionSortType.NEWEST,
    val isAnswered: Boolean?,
    val categoryNameList: List<String> = emptyList(),
    val offset: Int = 0,
    val limit: Int = 10,
)