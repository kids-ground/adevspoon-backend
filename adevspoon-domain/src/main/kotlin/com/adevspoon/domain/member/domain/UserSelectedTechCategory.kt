package com.adevspoon.domain.member.domain

import jakarta.persistence.*
import java.io.Serializable

// Fixme 빈 테이블 - 삭제필요
@Entity
@Table(name = "UserSelectedTechCategory", schema = "adevspoon")
class UserSelectedTechCategory(
    @EmbeddedId
    val id: UserSelectedTechCategoryId? = null
)

@Embeddable
class UserSelectedTechCategoryId(
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    val user: User? = null,

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    val category: TechCategory? = null
): Serializable