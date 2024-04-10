package com.adevspoon.domain.board.dto.response

import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.domain.BoardTagEntity
import com.adevspoon.domain.member.dto.response.MemberProfile
import java.time.LocalDateTime

data class BoardPost(
    val id: Long,
    var title: String,
    var content: String,
    val user: MemberProfile,
    var tag: BoardTagEntity,
    var likeCount: Int,
    var commentCount: Int,
    var viewCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(board: BoardPostEntity, memberProfile: MemberProfile) = BoardPost(
            id = board.id,
            title = board.title ?: "",
            content = board.content ?: "",
            user = memberProfile,
            tag = board.tag!!,
            likeCount = board.likeCount ?: 0,
            commentCount = board.commentCount ?: 0,
            viewCount = board.viewCount ?: 0,
            createdAt = board.createdAt ?: LocalDateTime.now(),
            updatedAt = board.updatedAt ?: LocalDateTime.now()
        )
    }
}
