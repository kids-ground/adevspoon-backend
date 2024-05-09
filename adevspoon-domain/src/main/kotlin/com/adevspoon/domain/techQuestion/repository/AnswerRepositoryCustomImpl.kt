package com.adevspoon.domain.techQuestion.repository

import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.dto.response.AnswerActivityInfo
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import com.adevspoon.domain.techQuestion.domain.QAnswerEntity.answerEntity
import com.adevspoon.domain.techQuestion.domain.enums.AnswerStatus
import com.adevspoon.domain.techQuestion.dto.enums.QuestionAnswerListSortType
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AnswerRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : AnswerRepositoryCustom {
    override fun findQuestionAnswerList(
        questionId: Long,
        sort: QuestionAnswerListSortType,
        offset: Long,
        limit: Int,
    ): Slice<AnswerEntity> {
        val resultList = jpaQueryFactory.selectFrom(answerEntity)
            .where(
                isNotPrivate(),
                isQuestionId(questionId)
            )
            .join(answerEntity.user).fetchJoin()
            .join(answerEntity.question).fetchJoin()
            .orderBy(sortOrder(sort))
            .offset(offset)
            .limit(limit.toLong() + 1)
            .fetch()

        var hasNext = false
        if (resultList.size == limit + 1) {
            hasNext = true
            resultList.removeLast()
        }

        return SliceImpl(resultList, Pageable.unpaged(), hasNext)
    }

    override fun findAnswerCountsByMonthAndUser(year: Int, month: Int, user: UserEntity): List<AnswerActivityInfo> {
        val firstDayOfMonth = LocalDate.of(year, month, 1)
        val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth())

        val formattedDate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", answerEntity.createdAt, "%Y-%m-%d")
        val results = jpaQueryFactory.select(
                formattedDate.`as`("date"),
                answerEntity.id.count()
            )
            .from(answerEntity)
            .where(answerEntity.createdAt.between(firstDayOfMonth.atStartOfDay(), lastDayOfMonth.atTime(23, 59, 59))
                .and(answerEntity.user.eq(user)))
            .groupBy(formattedDate)
            .orderBy(formattedDate.asc())
            .fetch()

        return results.map { tuple ->
            val dateAsString = tuple.get(0, String::class.java)
            val date = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val count = tuple.get(1, Long::class.java)?.toInt() ?: 0
            AnswerActivityInfo(date, count)
        }

    }

    private fun isNotPrivate() = answerEntity.status.ne(AnswerStatus.PRIVATE)

    private fun isQuestionId(questionId: Long) = answerEntity.question.id.eq(questionId)

    private fun sortOrder(sort: QuestionAnswerListSortType) =
        when (sort) {
            QuestionAnswerListSortType.NEWEST -> answerEntity.createdAt.desc()
            QuestionAnswerListSortType.OLDEST -> answerEntity.createdAt.asc()
            QuestionAnswerListSortType.LIKE -> answerEntity.likeCnt.desc()
            QuestionAnswerListSortType.BEST -> answerEntity.likeCnt.desc()
        }
}