package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository: JpaRepository<AnswerEntity, Long> {

}