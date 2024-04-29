package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.sql.Date

interface AnswerRepository: JpaRepository<AnswerEntity, Long> {

    @Query("SELECT a FROM AnswerEntity a " +
            "LEFT JOIN FETCH a.question " +
            "LEFT JOIN FETCH a.user " +
            "WHERE a.id = :answerId")
    fun findWithQuestionAndUser(answerId: Long): AnswerEntity?

    @Query("SELECT DATE(a.createdAt) " +
            "FROM AnswerEntity a " +
            "WHERE a.user.id = :userId " +
            "GROUP BY DATE(a.createdAt) " +
            "ORDER BY DATE(a.createdAt) DESC " +
            "Limit 2")
    fun findAnsweredDateList(userId: Long): List<Date>
}