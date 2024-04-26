package com.adevspoon.api.answer.dto.response

import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.adevspoon.domain.techQuestion.dto.response.QuestionAnswerInfo
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime

data class AnswerInfoResponse(
    @JsonProperty("answer_id")
    val answerId: Long,
    @JsonProperty("question_id")
    val questionId: Long,
    val answer: String,
    @JsonProperty("like_cnt")
    val likeCnt: Int,
    @JsonProperty("is_liked")
    val isLiked: Boolean,

    @Schema(description = "질문을 발급받은 날짜")
    @JsonProperty("opne_date")
    val openDate: LocalDate,

    @Schema(description = "작성자 프로필")
    val user: MemberProfileResponse,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(answer: QuestionAnswerInfo) = AnswerInfoResponse(
            answerId = answer.answerId,
            questionId = answer.questionId,
            answer = answer.answer,
            likeCnt = answer.likeCnt,
            isLiked = answer.isLiked,
            openDate = answer.openDate,
            user = MemberProfileResponse.from(answer.user),
            createdAt = answer.createdAt,
            updatedAt = answer.updatedAt,
        )
    }
}