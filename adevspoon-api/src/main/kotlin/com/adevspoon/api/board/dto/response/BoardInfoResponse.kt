package com.adevspoon.api.board.dto.response

import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.adevspoon.domain.board.dto.response.BoardPost
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class BoardInfoResponse(
    val id: Long,
    @Schema(description = "게시글 태그 정보")
    val tag: BoardTagResponse,
    val title: String,
    val content: String,
    val likeCount: Int,
    val commentCount: Int,
    @Schema(description = "좋아요를 눌렀는지 여부")
    val isLiked: Boolean?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    @Schema(description = "게시글 작성자 프로필정보")
    val user: MemberProfileResponse
) {
    companion object {
        fun from(board: BoardPost, tag: BoardTagResponse, user: MemberProfileResponse) = BoardInfoResponse(
            id = board.id,
            tag = tag,
            title = board.title,
            content = board.content,
            likeCount = board.likeCount,
            commentCount = board.commentCount,
            isLiked = false,
            createdAt = board.createdAt,
            updatedAt = board.updatedAt,
            user = user
        )
    }
}
