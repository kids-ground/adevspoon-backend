package com.adevspoon.domain.member.domain

import jakarta.persistence.*
import java.io.Serializable

// Fixme 빈 테이블 - 삭제필요
@Entity
@Table(name = "UserSelectedTechCategory", schema = "adevspoon")
class UserSelectedTechCategoryEntity(
    @EmbeddedId
    val id: UserSelectedTechCategoryId? = null
)

@Embeddable
class UserSelectedTechCategoryId(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    val category: TechCategoryEntity? = null
): Serializable