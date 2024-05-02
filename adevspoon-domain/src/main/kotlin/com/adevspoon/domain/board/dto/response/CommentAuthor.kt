package com.adevspoon.domain.board.dto.response

import com.adevspoon.domain.board.domain.BoardCommentEntity
import com.adevspoon.domain.member.domain.UserEntity

data class CommentAuthor(
    val user: UserEntity,
    val comment: BoardCommentEntity
)
