package com.adevspoon.api.answer.service

import com.adevspoon.api.answer.dto.request.RegisterAnswerRequest
import com.adevspoon.api.answer.dto.response.AnswerInfoResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.techQuestion.service.AnswerDomainService

@ApplicationService
class AnswerService(
    private val answerDomainService: AnswerDomainService
) {
    fun registerAnswer(request: RegisterAnswerRequest, memberId: Long): AnswerInfoResponse {
        val questionAnswer = answerDomainService.registerQuestionAnswer(request.toCreateQuestionAnswer(memberId))
        return AnswerInfoResponse.from(questionAnswer)
    }
}