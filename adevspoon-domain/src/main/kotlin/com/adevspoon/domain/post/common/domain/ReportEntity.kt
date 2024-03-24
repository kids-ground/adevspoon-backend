package com.adevspoon.domain.post.common.domain

import com.adevspoon.domain.common.entity.LegacyBaseEntity
import com.adevspoon.domain.post.common.domain.enums.ReportReason
import com.adevspoon.domain.post.techAnswer.domain.AnswerEntity
import com.adevspoon.domain.member.domain.UserEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "reports", schema = "adevspoon")
class ReportEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Size(max = 50)
    @NotNull
    @Column(name = "post_type", nullable = false, length = 50)
    val postType: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    val user: UserEntity? = null,

    @Column(name = "reason")
    @Enumerated(EnumType.STRING)
    val reason: ReportReason? = null,

    @Column(name = "is_read")
    var isRead: Boolean? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    val post: AnswerEntity? = null,

    @Column(name = "boardPostId")
    val boardPostId: Long? = null,

    @Column(name = "boardCommentId")
    val boardCommentId: Long? = null
): LegacyBaseEntity()