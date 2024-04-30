package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.dto.IssuedQuestionFilter
import com.adevspoon.domain.techQuestion.dto.enums.IssuedQuestionSortType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface QuestionOpenRepositoryCustom {
    fun findQuestionOpenList(sort: IssuedQuestionSortType, filter: IssuedQuestionFilter, pageable: Pageable) : Slice<QuestionOpenEntity>
}