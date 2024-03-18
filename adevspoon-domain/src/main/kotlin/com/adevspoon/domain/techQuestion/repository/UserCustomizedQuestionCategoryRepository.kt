package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategoryId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query


interface UserCustomizedQuestionCategoryRepository: JpaRepository<UserCustomizedQuestionCategoryEntity, UserCustomizedQuestionCategoryId> {
    @Modifying(clearAutomatically = true)
    @Query("delete from UserCustomizedQuestionCategoryEntity qc where qc.id.user.id = :userId")
    fun deleteAllByUserId(userId: Long)
}