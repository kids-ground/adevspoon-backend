package com.adevspoon.domain.common.repository

import com.adevspoon.domain.common.entity.ReportEntity
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository: JpaRepository<ReportEntity, Long> {
    fun findAllByUserAndPost(user: UserEntity, post: AnswerEntity): List<ReportEntity>
}