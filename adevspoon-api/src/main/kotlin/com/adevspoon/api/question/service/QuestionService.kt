package com.adevspoon.api.question.service

import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.api.question.dto.response.QuestionInfoResponse
import com.adevspoon.domain.techQuestion.service.QuestionDomainService
import java.time.LocalDate

@ApplicationService
class QuestionService(
    private val questionDomainService: QuestionDomainService
) {
    fun getTodayQuestion(memberId: Long): QuestionInfoResponse {
        return questionDomainService.getOrCreateTodayQuestion(memberId, LocalDate.now())
            .let {
                QuestionInfoResponse.from(it)
            }
    }

    fun getQuestionById(memberId: Long, questionId: Long): QuestionInfoResponse {
        return questionDomainService.getQuestion(memberId, questionId)
            .let {
                QuestionInfoResponse.from(it)
            }
    }
}