package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.QuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategoryId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query


interface UserCustomizedQuestionCategoryRepository: JpaRepository<UserCustomizedQuestionCategoryEntity, UserCustomizedQuestionCategoryId> {
    @Modifying(clearAutomatically = true)
    @Query("delete from UserCustomizedQuestionCategoryEntity qc where qc.id.user = :user")
    fun deleteAllByUser(user: UserEntity)

    @Query("select qc.id.category.id from UserCustomizedQuestionCategoryEntity qc where qc.id.user = :user")
    fun findAllSelectedCategoryIds(user: UserEntity): List<Long>

    @Query("select qc.id.category from UserCustomizedQuestionCategoryEntity qc where qc.id.user = :user")
    fun findAllSelectedCategory(user: UserEntity): List<QuestionCategoryEntity>
}