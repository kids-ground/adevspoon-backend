package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategory
import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategoryId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query


interface UserCustomizedQuestionCategoryRepository: JpaRepository<UserCustomizedQuestionCategory, UserCustomizedQuestionCategoryId> {
    @Modifying(clearAutomatically = true)
    @Query("delete from UserCustomizedQuestionCategory qc where qc.id.user.id = :userId")
    fun deleteAllByUserId(userId: Long)
}