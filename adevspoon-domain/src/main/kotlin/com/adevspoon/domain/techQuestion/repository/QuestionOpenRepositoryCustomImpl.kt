package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.techQuestion.domain.QAnswerEntity.answerEntity
import com.adevspoon.domain.techQuestion.domain.QQuestionEntity.questionEntity
import com.adevspoon.domain.techQuestion.domain.QQuestionOpenEntity.questionOpenEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.dto.IssuedQuestionFilter
import com.adevspoon.domain.techQuestion.dto.enums.IssuedQuestionSortType
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl

class QuestionOpenRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
): QuestionOpenRepositoryCustom {
    override fun findQuestionOpenList(
        sort: IssuedQuestionSortType,
        filter: IssuedQuestionFilter,
        pageable: Pageable
    ): Slice<QuestionOpenEntity> {
        val resultList = jpaQueryFactory.selectFrom(questionOpenEntity)
            .leftJoin(questionOpenEntity.question, questionEntity).fetchJoin()
            .leftJoin(questionOpenEntity.answer, answerEntity).fetchJoin()
            .where(
                questionOpenEntity.user.id.eq(filter.memberId),
                inCategory(filter.categoryIds),
                isAnswered(filter.isAnswered),
            )
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

    private fun inCategory(categoryIds: List<Long>) =
        if (categoryIds.isEmpty()) null
        else questionOpenEntity.question.categoryId.`in`(categoryIds)

    private fun isAnswered(isAnswered: Boolean?) =
        when (isAnswered) {
            true -> questionOpenEntity.answer.isNotNull
            false -> questionOpenEntity.answer.isNull
            else -> null
        }

    private fun sortOrder(sort: IssuedQuestionSortType) =
        when (sort) {
            IssuedQuestionSortType.NEWEST -> questionOpenEntity.openDate.desc()
            IssuedQuestionSortType.OLDEST -> questionOpenEntity.openDate.asc()
        }
}