package com.adevspoon.api.question.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime

data class QuestionInfoResponse(
    @JsonProperty("question_id")
    val questionId: Long,

    val question: String,
    val difficulty: Int,

    @JsonProperty("study_link")
    val studyLink: String,

    @JsonProperty("category")
    val categoryName: String,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,

    val tag: List<String>,
    @JsonProperty("open_date")
    val openDate: LocalDate,

    @JsonProperty("answer_id")
    val answerId: Long? = null,

    val isLast: Boolean = false,
)