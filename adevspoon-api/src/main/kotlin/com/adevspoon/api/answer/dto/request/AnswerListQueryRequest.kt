package com.adevspoon.api.answer.dto.request

import com.adevspoon.domain.techQuestion.dto.request.GetQuestionAnswerList
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

// TODO: Enum Validation 필요
data class AnswerListQueryRequest(
    @Schema(description = "무조건 answer", example = "answer", nullable = true, defaultValue = "answer")
    val type: AnswerType = AnswerType.ANSWER,
    @Schema(description = "게시물 정렬 기준", example = "newest", nullable = true, defaultValue = "newest")
    val sort: AnswerSortType = AnswerSortType.NEWEST,
    @Schema(description = "질문 id, sort가 best인 경우 null", nullable = true)
    val questionId: Long? = null,

    @Schema(nullable = true, defaultValue = "0")
    @field:PositiveOrZero(message = "offset은 0이상이어야 합니다.")
    val offset: Int = 0,
    @Schema(nullable = true, defaultValue = "10")
    @field:Positive(message = "limit은 양수여야 합니다.")
    val limit: Int = 10,
) {
    fun toGetQuestionAnswerList(memberId: Long) = GetQuestionAnswerList(
        memberId = memberId,
        questionId = questionId,
        sort = sort.toQuestionAnswerListSortType(),
        offset = offset,
        limit = limit
    )
    fun nextUrl() = ServletUriComponentsBuilder.fromCurrentRequest()
        .replaceQueryParam("offset", offset + limit)
        .build()
        .toUriString()
}