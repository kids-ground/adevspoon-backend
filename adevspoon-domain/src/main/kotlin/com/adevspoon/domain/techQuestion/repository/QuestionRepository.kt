package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository: JpaRepository<QuestionEntity, Long> {


}