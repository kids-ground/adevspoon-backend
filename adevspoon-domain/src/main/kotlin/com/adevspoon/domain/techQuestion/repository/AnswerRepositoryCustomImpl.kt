package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import com.adevspoon.domain.techQuestion.domain.QAnswerEntity.answerEntity
import com.adevspoon.domain.techQuestion.dto.enums.QuestionAnswerListSortType
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl

class AnswerRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
): AnswerRepositoryCustom {
    override fun findQuestionAnswerList(
        questionId: Long,
        sort: QuestionAnswerListSortType,
        pageable: Pageable
    ): Slice<AnswerEntity> {
        val resultList = jpaQueryFactory.selectFrom(answerEntity)
            .where(answerEntity.question.id.eq(questionId))
            .join(answerEntity.user).fetchJoin()
            .join(answerEntity.question).fetchJoin()
            .orderBy(sortOrder(sort))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong() + 1)
            .fetch()

        var hasNext = false
        if (resultList.size == pageable.pageSize + 1) {
            hasNext = true
            resultList.removeLast()
        }

        return SliceImpl(resultList, pageable, hasNext)
    }

    private fun sortOrder(sort: QuestionAnswerListSortType) =
        when (sort) {
            QuestionAnswerListSortType.NEWEST -> answerEntity.createdAt.desc()
            QuestionAnswerListSortType.OLDEST -> answerEntity.createdAt.asc()
            QuestionAnswerListSortType.LIKE -> answerEntity.likeCnt.desc()
            QuestionAnswerListSortType.BEST -> answerEntity.likeCnt.desc()
        }
}