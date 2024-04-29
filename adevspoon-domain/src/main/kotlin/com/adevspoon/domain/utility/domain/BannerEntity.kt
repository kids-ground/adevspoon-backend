package com.adevspoon.domain.utility.domain

import com.adevspoon.domain.common.entity.LegacyBaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(name = "banners", schema = "adevspoon")
class BannerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Size(max = 255)
    @Column(name = "name")
    val name: String? = null,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "url")
    var url: String? = null,

    @Column(name = "open_date")
    var openDate: LocalDateTime? = null,

    @Column(name = "close_date")
    var closeDate: LocalDateTime? = null
): LegacyBaseEntity()