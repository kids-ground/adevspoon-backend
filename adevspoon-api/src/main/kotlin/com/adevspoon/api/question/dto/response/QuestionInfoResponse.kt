package com.adevspoon.api.question.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime

data class QuestionInfoResponse(
    @JsonProperty("question_id")
    val questionId: Long,

    val question: String,

    @Schema(description = "난이도, 현재는 사용되지 않음")
    val difficulty: Int = 0,

    @Schema(description = "해당 질문과 연관되는 링크")
    @JsonProperty("study_link")
    val studyLink: String,

    @Schema(description = "해당 질문의 카테고리 이름")
    @JsonProperty("category")
    val categoryName: String,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,

    @Schema(description = "해당 질문에 대한 태그 리스트, 현재는 사용되지 않음")
    val tag: List<String>,

    @Schema(description = "질문을 발급받은 날짜", example = "2021-01-01")
    @JsonProperty("open_date")
    val openDate: LocalDate,

    @Schema(description = "해당 질문에 대한 답변이 존재한다면 답변 id, 없다면 null")
    @JsonProperty("answer_id")
    val answerId: Long? = null,

    @Schema(description = "해당 카테고리에 더 이상 받을 질문이 없는지 여부")
    val isLast: Boolean = false,
)