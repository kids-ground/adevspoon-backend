package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.dto.response.CategoryQuestionCountDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuestionRepository: JpaRepository<QuestionEntity, Long> {
    @Query("SELECT q.id from QuestionEntity q where q.categoryId in :categoryIds")
    fun findAllQuestionIds(categoryIds: List<Long>): Set<Long>

    @Query("SELECT new com.adevspoon.domain.techQuestion.dto.response.CategoryQuestionCountDto(q.categoryId, COUNT(q)) " +
            "FROM QuestionEntity q " +
            "GROUP BY q.categoryId")
    fun findQuestionCountGroupByCategory(): List<CategoryQuestionCountDto>
}