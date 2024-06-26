package com.adevspoon.domain.board.domain

import com.adevspoon.domain.board.exception.NegativeLikeCountExceptionForBoard
import com.adevspoon.domain.common.entity.BaseEntity
import com.adevspoon.domain.member.domain.UserEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "BoardPost", schema = "adevspoon")
class BoardPostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId", nullable = false)
    val id: Long = 0,

    @Size(max = 255)
    @Column(name = "title")
    var title: String? = null,

    @NotNull
    @Column(name = "content", nullable = false)
    var content: String,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    var tag: BoardTagEntity,

    @NotNull
    @Column(name = "likeCount", nullable = false)
    var likeCount: Int,

    @NotNull
    @Column(name = "commentCount", nullable = false)
    var commentCount: Int,

    @NotNull
    @Column(name = "viewCount", nullable = false)
    var viewCount: Int
) : BaseEntity() {
    fun increaseViewCount() {
        this.viewCount++
    }

    fun increaseLikeCount() {
        this.likeCount++
    }

    fun decreaseLikeCount() {
        if (likeCount - 1 < 0) {
            throw NegativeLikeCountExceptionForBoard("board_post", id)
        }
        this.likeCount--
    }

    fun updateTitleAndContent(title: String?, content: String?) {
        title?.let { this.title = it }
        content?.let { this.content = it }
    }

    fun updateTag(newTag: BoardTagEntity) {
        tag = newTag
    }
}