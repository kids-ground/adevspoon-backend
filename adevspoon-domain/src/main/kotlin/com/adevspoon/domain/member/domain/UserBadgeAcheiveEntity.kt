package com.adevspoon.domain.member.domain

import com.adevspoon.domain.common.entity.BaseEntity
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "UserBadgeAcheive", schema = "adevspoon")
class UserBadgeAcheiveEntity(
    @EmbeddedId
    val id: UserBadgeAcheiveId? = null
): BaseEntity()

@Embeddable
class UserBadgeAcheiveId(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "badgeId", nullable = false)
    val badge: BadgeEntity? = null
): Serializable