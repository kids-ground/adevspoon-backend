package com.adevspoon.domain.post.board.domain

import com.adevspoon.domain.common.domain.BaseEntity
import com.adevspoon.domain.user.domain.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant

@Entity
@Table(name = "BoardComment", schema = "adevspoon")
class BoardComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId", nullable = false)
    val id: Long = 0,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "postId", nullable = false)
    val post: BoardPost? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "userId")
    val user: User? = null,

    @NotNull
    @Column(name = "content", nullable = false)
    var content: String? = null,

    @NotNull
    @Column(name = "likeCount", nullable = false)
    var likeCount: Int? = null
): BaseEntity()