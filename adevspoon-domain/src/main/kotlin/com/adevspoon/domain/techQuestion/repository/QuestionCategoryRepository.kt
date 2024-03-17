package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.QuestionCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuestionCategoryRepository: JpaRepository<QuestionCategory, Long> {
    @Query("SELECT qc from QuestionCategory qc where qc.id in :ids")
    fun findQuestionCategoryByIds(ids: List<Long>): List<QuestionCategory>
}