package com.adevspoon.domain.board.repository

import com.adevspoon.domain.board.domain.BoardCommentEntity
import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.dto.response.CommentAuthor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardCommentRepository : JpaRepository<BoardCommentEntity, Long> {

    @Query("SELECT NEW com.adevspoon.domain.board.dto.response.CommentAuthor(c.user, c) FROM BoardCommentEntity c " +
        "WHERE c.post = :post ORDER BY c.id DESC")
    fun findCommentAuthorsByPost(post: BoardPostEntity): List<CommentAuthor>
}
