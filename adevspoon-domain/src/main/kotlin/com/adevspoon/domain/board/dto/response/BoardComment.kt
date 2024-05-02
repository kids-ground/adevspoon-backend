package com.adevspoon.domain.board.dto.response

import com.adevspoon.domain.board.domain.BoardCommentEntity
import com.adevspoon.domain.member.dto.response.MemberProfile
import java.time.LocalDateTime

data class BoardComment (
    val id: Long,
    val postId: Long,
    val content: String,
    val user: MemberProfile,
    val isLiked: Boolean,
    val isMine: Boolean?,
    val likeCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(comment : BoardCommentEntity, memberProfile: MemberProfile, isLike: Boolean, isMine: Boolean?) = BoardComment(
            id = comment.id,
            postId = comment.post.id,
            content = comment.content,
            user = memberProfile,
            isLiked = isLike,
            isMine = isMine,
            likeCount = comment.likeCount,
            createdAt = comment.createdAt ?: LocalDateTime.now(),
            updatedAt = comment.updatedAt ?: LocalDateTime.now()
        )
    }
}