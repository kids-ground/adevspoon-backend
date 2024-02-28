package com.adevspoon.domain.member.domain

import com.adevspoon.domain.domain.LegacyBaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "Badges", schema = "adevspoon")
class Badge(
    @Id
    @Column(name = "id", nullable = false)
    val id: Int? = null,

    @Size(max = 50)
    @Column(name = "name", length = 50)
    val name: String? = null,

    @Size(max = 255)
    @Column(name = "description")
    var description: String? = null,

    @Column(name = "imageUrl")
    var imageUrl: String? = null,

    @Size(max = 64)
    @Column(name = "criteria", length = 64)
    var criteria: String? = null,

    @Column(name = "criteriaValue")
    var criteriaValue: Int? = null,

    @Column(name = "thumbnailUrl")
    var thumbnailUrl: String? = null,

    @Column(name = "silhouetteUrl")
    var silhouetteUrl: String? = null,
): LegacyBaseEntity()