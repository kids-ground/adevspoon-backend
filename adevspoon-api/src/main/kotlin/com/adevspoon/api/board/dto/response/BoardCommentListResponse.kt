package com.adevspoon.api.board.dto.response

import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.adevspoon.domain.board.dto.response.BoardComment

data class BoardCommentListResponse(
    val list: List<BoardCommentResponse>,
) {
    companion object {
        fun from(commentList: List<BoardComment>) : BoardCommentListResponse{
            val commentResponseList = commentList.map { comment ->
                BoardCommentResponse(
                id = comment.id,
                postId = comment.postId,
                content = comment.content,
                user = MemberProfileResponse.from(comment.user),
                isLiked = comment.isLiked,
                isMine = comment.isMine,
                likeCount = comment.likeCount,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt
                )
            }

            return BoardCommentListResponse(commentResponseList)
        }
    }
}