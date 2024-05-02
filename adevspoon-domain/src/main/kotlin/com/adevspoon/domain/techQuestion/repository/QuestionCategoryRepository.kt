package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.QuestionCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuestionCategoryRepository: JpaRepository<QuestionCategoryEntity, Long> {
    @Query("SELECT qc from QuestionCategoryEntity qc where qc.id in :ids")
    fun findQuestionCategoryByIds(ids: List<Long>): List<QuestionCategoryEntity>

    @Query("SELECT qc from QuestionCategoryEntity qc where qc.category in :names")
    fun findByNames(names: List<String>): List<QuestionCategoryEntity>

    @Query("SELECT qc.id from QuestionCategoryEntity qc")
    fun findAllIds(): List<Long>
}