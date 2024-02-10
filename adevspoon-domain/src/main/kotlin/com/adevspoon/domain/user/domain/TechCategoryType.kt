package com.adevspoon.domain.user.domain

import jakarta.persistence.*
import jakarta.validation.constraints.Size

// FIXME - 사용하지 않는 테이블(삭제 필요)
@Entity
@Table(name = "TechCategoryType", schema = "adevspoon")
class TechCategoryType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0,

    @Size(max = 30)
    @Column(name = "name", length = 30)
    var name: String? = null
)