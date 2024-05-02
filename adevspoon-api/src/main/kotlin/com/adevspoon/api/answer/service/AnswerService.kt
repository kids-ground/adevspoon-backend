package com.adevspoon.api.answer.service

import com.adevspoon.api.answer.dto.request.AnswerListQueryRequest
import com.adevspoon.api.answer.dto.request.AnswerSortType
import com.adevspoon.api.answer.dto.request.AnswerUpdateRequest
import com.adevspoon.api.answer.dto.request.RegisterAnswerRequest
import com.adevspoon.api.answer.dto.response.AnswerInfoResponse
import com.adevspoon.api.answer.dto.response.AnswerListResponse
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

    fun getAnswerList(request: AnswerListQueryRequest, memberId: Long): AnswerListResponse {
        val answerList = if (request.sort == AnswerSortType.BEST) {
            answerDomainService.getTodayBestAnswerList(memberId)
        } else {
            answerDomainService.getAnswerList(request.toGetQuestionAnswerList(memberId))
        }

        return AnswerListResponse(
            answerList.list.map { AnswerInfoResponse.from(it) },
            if (answerList.hasNext) request.getNextUrl() else null
        )
    }

    fun modifyAnswer(answerId: Long, request: AnswerUpdateRequest, memberId: Long): AnswerInfoResponse {
        val questionAnswer = answerDomainService.modifyAnswerInfo(ModifyQuestionAnswer(memberId, answerId, request.content))
        return AnswerInfoResponse.from(questionAnswer)
    }

    fun likeAnswer(answerId: Long, memberId: Long, isLiked: Boolean): String {
        answerDomainService.toggleAnswerLike(answerId, memberId, isLiked)
        return if (isLiked) "답변:$answerId 를 좋아요 완료" else "답변:$answerId 좋아요 취소 완료"
    }

    fun reportAnswer(answerId: Long, memberId: Long): String {
        answerDomainService.reportAnswer(answerId, memberId)
        return "답변:$answerId 에 대한 신고가 접수되었습니다."
    }
}