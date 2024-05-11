package com.adevspoon.domain.common.entity

import com.adevspoon.domain.common.entity.enums.ReportReason
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
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
    val postType: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @Column(name = "reason", columnDefinition = "ENUM('abuse','spammer','obscene','scam', 'political_agitation', 'illegal_ads_and_sales', 'etc')")
    val reason: ReportReason = ReportReason.ETC,

    @Column(name = "is_read")
    var isRead: Boolean? = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    val post: AnswerEntity? = null,

    @Column(name = "boardPostId")
    val boardPostId: Long? = null,

    @Column(name = "boardCommentId")
    val boardCommentId: Long? = null
) : LegacyBaseEntity() {
    fun getContentId(): Long = boardPostId ?: boardCommentId ?: post?.id ?: -1
}