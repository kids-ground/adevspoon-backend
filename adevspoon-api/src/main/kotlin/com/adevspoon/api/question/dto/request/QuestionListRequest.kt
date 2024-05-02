package com.adevspoon.api.question.dto.request

import com.adevspoon.domain.techQuestion.dto.request.GetIssuedQuestionList
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


data class QuestionListRequest(
    @Schema(description = "정렬. 기본값은 NEWEST(최신수)", nullable = true, defaultValue = "NEWEST")
    val sort: QuestionSortType = QuestionSortType.NEWEST,
    @Schema(description = "등록한 답변만 가져올것인지 여부. (null일 경우 전부)", nullable = true)
    val isAnswered: Boolean?,
    @Schema(description = "필터링할 카테고리 리스트. (null일 경우 전부)", nullable = true)
    val category: List<String> = emptyList(),
    val offset: Int = 0,
    val limit: Int = 10,
) {
    fun toGetIssuedQuestionList(memberId: Long) = GetIssuedQuestionList(
        memberId = memberId,
        sort = sort.toIssuedQuestionSortType(),
        isAnswered = isAnswered,
        categoryNameList = category,
        offset = offset,
        limit = limit,
    )

    fun getNextUrl() = ServletUriComponentsBuilder.fromCurrentRequest()
            .replaceQueryParam("offset", offset + limit)
            .build()
            .toUriString()
}