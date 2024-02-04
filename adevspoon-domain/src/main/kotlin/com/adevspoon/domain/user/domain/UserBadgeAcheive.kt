package com.adevspoon.domain.user.domain

import com.adevspoon.domain.common.domain.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.io.Serializable
import java.time.Instant
import java.util.*

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