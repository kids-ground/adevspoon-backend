package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuestionRepository: JpaRepository<QuestionEntity, Long> {
    @Query("SELECT q.id from QuestionEntity q where q.categoryId in :categoryIds")
    fun findAllQuestionIds(categoryIds: List<Long>): Set<Long>
}