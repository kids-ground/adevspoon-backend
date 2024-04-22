package com.adevspoon.api.question.service

import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.api.question.dto.response.QuestionCategoryResponse
import com.adevspoon.api.question.dto.response.QuestionInfoResponse
import com.adevspoon.domain.techQuestion.dto.request.GetTodayQuestion
import com.adevspoon.domain.techQuestion.service.QuestionDomainService
import java.time.LocalDate

@ApplicationService
class QuestionService(
    private val questionDomainService: QuestionDomainService
) {
    fun getTodayQuestion(memberId: Long): QuestionInfoResponse {
        return questionDomainService.getOrCreateTodayQuestion(GetTodayQuestion(memberId, LocalDate.now()))
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

    fun getQuestionCategories(memberId: Long): List<QuestionCategoryResponse> {
        return questionDomainService.getQuestionCategories(memberId)
            .map {
                QuestionCategoryResponse.from(it)
            }
    }
}