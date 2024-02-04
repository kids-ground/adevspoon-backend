package com.adevspoon.domain.techQuestion.domain

import com.adevspoon.domain.common.domain.LegacyBaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

// FIXME - 현재 사용중인 것 같지 않음
@Entity
@Table(name = "tags", schema = "adevspoon")
class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Size(max = 255)
    @NotNull
    @Column(name = "tag", nullable = false)
    val tag: String? = null
): LegacyBaseEntity()