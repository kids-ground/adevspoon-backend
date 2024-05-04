package com.adevspoon.domain.board.repository

import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.domain.QBoardPostEntity.boardPostEntity
import com.adevspoon.domain.board.domain.QBoardTagEntity.boardTagEntity
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository

@Repository
class BoardPostRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : BoardPostRepositoryCustom {
    override fun findByTagsAndUserIdWithCursor(
        tags: List<Int>?,
        startPostId: Long?,
        targetUserId: Long?,
        pageable: Pageable
    ): Slice<BoardPostEntity> {
        val boardPosts = jpaQueryFactory.selectFrom(boardPostEntity)
            .apply {
                if (!tags.isNullOrEmpty()) {
                    join(boardPostEntity.tag, boardTagEntity)
                        .where(boardTagEntity.id.`in`(tags))
                } else {
                    jpaQueryFactory.selectFrom(boardTagEntity).fetch()
                }
            }
            .where(
                userIdCondition(targetUserId),
                postIdLessThanCondition(startPostId)
            )
            .orderBy(boardPostEntity.id.desc())
            .limit((pageable.pageSize.toLong() + 1))
            .fetch()

        val hasNext = boardPosts.size > pageable.pageSize
        val sliceContent = if (hasNext) boardPosts.subList(0, pageable.pageSize) else boardPosts

        return SliceImpl(sliceContent, pageable, hasNext)
    }

    private fun userIdCondition(userId: Long?): BooleanExpression? {
        return userId?.let { boardPostEntity.user.id.eq(it) }
    }

    private fun postIdLessThanCondition(postId: Long?): BooleanExpression? {
        return postId?.let { boardPostEntity.id.lt(it) }
    }


}