package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.dto.response.CategoryQuestionCountDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuestionOpenRepository: JpaRepository<QuestionOpenEntity, Long>, QuestionOpenRepositoryCustom {
    @Query("SELECT qo " +
            "FROM QuestionOpenEntity qo " +
                "LEFT JOIN FETCH qo.question " +
                "LEFT JOIN FETCH qo.answer " +
            "WHERE qo.user = :user " +
            "ORDER BY qo.createdAt DESC " +
            "LIMIT 1")
    fun findLatestWithQuestionAndAnswer(user: UserEntity): QuestionOpenEntity?

    @Query("SELECT qo " +
            "FROM QuestionOpenEntity qo " +
                "LEFT JOIN FETCH qo.question " +
                "LEFT JOIN FETCH qo.answer " +
            "WHERE qo.question = :question AND qo.user = :user ")
    fun findByQuestionAndUser(question: QuestionEntity, user: UserEntity): QuestionOpenEntity?

    @Query("SELECT qo.question.id FROM QuestionOpenEntity qo WHERE qo.user = :user")
    fun findAllIssuedQuestionIds(user: UserEntity): Set<Long>

    @Query("SELECT new com.adevspoon.domain.techQuestion.dto.response.CategoryQuestionCountDto(q.question.categoryId, COUNT(q)) " +
            "FROM QuestionOpenEntity q " +
            "WHERE q.user = :user " +
            "GROUP BY q.question.categoryId ")
    fun findIssuedQuestionGroupByCategory(user: UserEntity): List<CategoryQuestionCountDto>
}