package com.adevspoon.domain.member.domain

import com.adevspoon.domain.domain.BaseEntity
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "UserBadgeAcheive", schema = "adevspoon")
class UserBadgeAcheive(
    @EmbeddedId
    val id: UserBadgeAcheiveId? = null
): BaseEntity()

@Embeddable
class UserBadgeAcheiveId(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    val user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "badgeId", nullable = false)
    val badge: Badge? = null
): Serializable