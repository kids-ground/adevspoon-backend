package com.adevspoon.api.answer.dto.response

import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime

data class AnswerInfoResponse(
    @JsonProperty("answer_id")
    val answerId: String,
    @JsonProperty("question_id")
    val questionId: String,
    val answer: String,
    @JsonProperty("like_cnt")
    val likeCnt: Int,

    @Schema(description = "질문을 발급받은 날짜")
    @JsonProperty("opne_date")
    val openDate: LocalDate,

    @Schema(description = "작성자 프로필")
    val user: MemberProfileResponse,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
)