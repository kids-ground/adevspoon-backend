package com.adevspoon.domain.post.common.domain

import com.adevspoon.domain.post.techAnswer.domain.AnswerEntity
import com.adevspoon.domain.member.domain.UserEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "likes", schema = "adevspoon")
@EntityListeners(AuditingEntityListener::class)
class LikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    val user: UserEntity? = null,

    @Size(max = 50)
    @NotNull
    @Column(name = "post_type", nullable = false, length = 50)
    val postType: String? = null,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    val post: AnswerEntity? = null,

    @Column(name = "boardPostId")
    val boardPostId: Long? = null,

    @Column(name = "boardCommentId")
    val boardCommentId: Long? = null
)