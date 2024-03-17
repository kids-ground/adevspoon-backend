package com.adevspoon.domain.post.board.domain

import com.adevspoon.domain.domain.BaseEntity
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
    var content: String? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    var tag: BoardTagEntity? = null,

    @NotNull
    @Column(name = "likeCount", nullable = false)
    var likeCount: Int? = null,

    @NotNull
    @Column(name = "commentCount", nullable = false)
    var commentCount: Int? = null,

    @NotNull
    @Column(name = "viewCount", nullable = false)
    var viewCount: Int? = null
): BaseEntity()