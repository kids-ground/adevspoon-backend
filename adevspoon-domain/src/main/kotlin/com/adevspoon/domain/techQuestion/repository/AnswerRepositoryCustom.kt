package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import com.adevspoon.domain.techQuestion.dto.enums.QuestionAnswerListSortType
import org.springframework.data.domain.Slice

interface AnswerRepositoryCustom {
    fun findQuestionAnswerList(questionId: Long, sort: QuestionAnswerListSortType, offset: Long, limit: Int): Slice<AnswerEntity>
}