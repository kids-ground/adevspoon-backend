package com.adevspoon.domain.dbcore.user.domain

import jakarta.persistence.*
import jakarta.validation.constraints.Size

// FIXME - 빈 테이블(삭제 필요)
@Entity
@Table(name = "TechCategory", schema = "adevspoon")
class TechCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeId")
    var type: TechCategoryType? = null,

    @Size(max = 30)
    @Column(name = "name", length = 30)
    var name: String? = null
)