package com.adevspoon.api.answer.dto.request

import com.adevspoon.domain.techQuestion.dto.request.CreateQuestionAnswer
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

data class RegisterAnswerRequest(
    @Schema(description = "답변을 단 질문의 id", nullable = false)
    val questionId: Long,
    @Schema(description = "작성한 답변", nullable = false)
    @field:Size(min = 30, max = 500, message = "content는 30자 이상 500자 이하여야 합니다.")
    val content: String,
    @Schema(description = "무조건 answer", example = "answer", nullable = true, defaultValue = "answer")
    val type: AnswerType = AnswerType.ANSWER,
) {
    fun toCreateQuestionAnswer(memberId: Long) = CreateQuestionAnswer(
        requestMemberId = memberId,
        questionId = questionId,
        answer = content,
    )
}