package com.adevspoon.domain.common.repository

import com.adevspoon.domain.common.entity.LikeEntity
import com.adevspoon.domain.common.entity.QLikeEntity.likeEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl

class LikeRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
): LikeRepositoryCustom {
    override fun findAnswerLikeList(memberId: Long, startId: Long?, take: Int): Slice<LikeEntity> {
        val resultList = jpaQueryFactory.selectFrom(likeEntity)
            .join(likeEntity.answer).fetchJoin()
            .join(likeEntity.answer.question).fetchJoin()
            .join(likeEntity.answer.user).fetchJoin()
            .where(
                isMemberId(memberId),
                isAnswer(),
                ltStartId(startId)
            )
            .orderBy(likeEntity.id.desc())
            .limit(take.toLong() + 1)
            .fetch()

        var hasNext = false
        if (resultList.size == take + 1) {
            hasNext = true
            resultList.removeLast()
        }

        return SliceImpl(resultList, Pageable.unpaged(), hasNext)
    }

    private fun isMemberId(memberId: Long) = likeEntity.user.id.eq(memberId)

    private fun isAnswer() = likeEntity.answer.isNotNull
        .and(likeEntity.postType.eq("answer"))

    private fun ltStartId(startId: Long?) =
        if (startId == null || startId == 0L) null
        else likeEntity.id.lt(startId)
}