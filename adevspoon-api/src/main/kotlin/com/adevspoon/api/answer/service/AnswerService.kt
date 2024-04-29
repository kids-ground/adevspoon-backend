package com.adevspoon.api.answer.service

import com.adevspoon.api.answer.dto.request.AnswerUpdateRequest
import com.adevspoon.api.answer.dto.request.RegisterAnswerRequest
import com.adevspoon.api.answer.dto.response.AnswerInfoResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.techQuestion.dto.request.ModifyQuestionAnswer
import com.adevspoon.domain.techQuestion.service.AnswerDomainService

@ApplicationService
class AnswerService(
    private val answerDomainService: AnswerDomainService
) {
    fun registerAnswer(request: RegisterAnswerRequest, memberId: Long): AnswerInfoResponse {
        val questionAnswer = answerDomainService.registerQuestionAnswer(request.toCreateQuestionAnswer(memberId))
        return AnswerInfoResponse.from(questionAnswer)
    }

    fun getAnswerDetail(answerId: Long, memberId: Long): AnswerInfoResponse {
        return AnswerInfoResponse
            .from(
                answerDomainService.getAnswerDetail(answerId, memberId)
            )
    }

    fun modifyAnswer(answerId: Long, request: AnswerUpdateRequest, memberId: Long): AnswerInfoResponse {
        val questionAnswer = answerDomainService.modifyAnswerInfo(ModifyQuestionAnswer(memberId, answerId, request.content))
        return AnswerInfoResponse.from(questionAnswer)
    }
}