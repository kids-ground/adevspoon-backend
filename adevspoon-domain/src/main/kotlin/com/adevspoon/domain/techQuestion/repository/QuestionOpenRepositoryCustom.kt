package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.dto.IssuedQuestionFilter
import com.adevspoon.domain.techQuestion.dto.enums.IssuedQuestionSortType
import org.springframework.data.domain.Slice

interface QuestionOpenRepositoryCustom {
    fun findQuestionOpenList(filter: IssuedQuestionFilter, sort: IssuedQuestionSortType, offset: Long, limit: Int) : Slice<QuestionOpenEntity>
}