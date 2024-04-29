package com.adevspoon.domain.techQuestion.dto.response

import com.adevspoon.domain.member.dto.response.MemberProfile
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import java.time.LocalDate
import java.time.LocalDateTime

data class QuestionAnswerInfo(
    val answerId: Long,
    val questionId: Long,
    val answer: String,
    val likeCnt: Int = 0,
    val isLiked: Boolean = false,
    val openDate: LocalDate,
    val user: MemberProfile,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(
            answer: AnswerEntity,
            member: MemberProfile,
            questionOpenDate: LocalDate,
            isLiked: Boolean = false,
        ) = QuestionAnswerInfo(
            answerId = answer.id,
            questionId = answer.question.id,
            answer = answer.answer ?: "",
            likeCnt = answer.likeCnt,
            isLiked = isLiked,
            openDate = questionOpenDate,
            user = member,
            createdAt = answer.createdAt ?: LocalDateTime.now(),
            updatedAt = answer.updatedAt ?: LocalDateTime.now(),
        )
    }
}