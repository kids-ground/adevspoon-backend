package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.dto.response.AnswerActivityInfo
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import com.adevspoon.domain.techQuestion.dto.enums.QuestionAnswerListSortType
import org.springframework.data.domain.Slice

interface AnswerRepositoryCustom {
    fun findQuestionAnswerList(questionId: Long, sort: QuestionAnswerListSortType, offset: Long, limit: Int): Slice<AnswerEntity>

    fun findAnswerCountsByMonthAndUser(year: Int, month: Int, user: UserEntity): List<AnswerActivityInfo>
}