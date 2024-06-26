package com.adevspoon.domain.board.domain

import com.adevspoon.domain.board.exception.NegativeLikeCountExceptionForBoard
import com.adevspoon.domain.common.entity.BaseEntity
import com.adevspoon.domain.member.domain.UserEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "BoardComment", schema = "adevspoon")
class BoardCommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId", nullable = false)
    val id: Long = 0,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "postId", nullable = false)
    val post: BoardPostEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "userId")
    val user: UserEntity,

    @NotNull
    @Column(name = "content", nullable = false)
    var content: String,

    @NotNull
    @Column(name = "likeCount", nullable = false)
    var likeCount: Int = 0
) : BaseEntity() {
    fun increaseLikeCount() {
        this.likeCount++
    }

    fun decreaseLikeCount() {
        if (likeCount - 1 < 0) {
            throw NegativeLikeCountExceptionForBoard("board_comment", id)
        }
        this.likeCount--
    }
}