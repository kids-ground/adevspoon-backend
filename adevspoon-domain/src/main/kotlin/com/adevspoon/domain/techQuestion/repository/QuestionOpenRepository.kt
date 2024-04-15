package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuestionOpenRepository: JpaRepository<QuestionOpenEntity, Long> {
    @Query("SELECT qo " +
            "FROM QuestionOpenEntity qo " +
                "join fetch qo.question " +
                "join fetch qo.answer " +
            "WHERE qo.user.id = :memberId " +
            "ORDER BY qo.createdAt DESC " +
            "LIMIT 1")
    fun findLatest(memberId: Long): QuestionOpenEntity?

    @Query("SELECT qo " +
            "FROM QuestionOpenEntity qo " +
                "join fetch qo.question " +
                "join fetch qo.answer " +
            "WHERE qo.question = :question AND qo.user = :user ")
    fun findByQuestionAndUser(question: QuestionEntity, user: UserEntity): QuestionOpenEntity?
}