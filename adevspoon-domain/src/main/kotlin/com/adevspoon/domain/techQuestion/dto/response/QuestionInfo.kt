package com.adevspoon.domain.techQuestion.dto.response

import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import java.time.LocalDate
import java.time.LocalDateTime

data class QuestionInfo(
    val questionId: Long,
    val question: String,
    val difficulty: Int = 0,
    val studyLink: String,
    val categoryName: String,

    val tag: List<String>? = null,
    val openDate: LocalDate,
    val answerId: Long? = null,
    val isLast: Boolean = false,

    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(open: QuestionOpenEntity, categoryName: String, isLast: Boolean = false) =
            QuestionInfo(
                questionId = open.question.id,
                question = open.question.question ?: "",
                difficulty = open.question.difficulty ?: 0,
                studyLink = open.question.studyLink ?: "",
                categoryName = categoryName,
                tag = arrayListOf(),
                openDate = open.openDate.toLocalDate(),
                answerId = open.answer?.id,
                isLast = isLast,
                createdAt = open.createdAt ?: LocalDateTime.now(),
                updatedAt = open.updatedAt ?: LocalDateTime.now(),
            )
    }
}