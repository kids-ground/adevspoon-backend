package com.adevspoon.api.answer.dto.response

import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class AnswerInfoResponse(
    @JsonProperty("answer_id")
    val answerId: String,
    @JsonProperty("question_id")
    val questionId: String,
    val answer: String,
    @JsonProperty("like_cnt")
    val likeCnt: Int,
    @JsonProperty("opne_date")
    val openDate: LocalDate,
    @JsonProperty("created_at")
    val createdAt: String,
    @JsonProperty("updated_at")
    val updatedAt: String,
    val user: MemberProfileResponse
)