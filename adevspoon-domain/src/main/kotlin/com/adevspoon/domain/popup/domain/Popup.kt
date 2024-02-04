package com.adevspoon.domain.popup.domain

import com.adevspoon.domain.common.domain.LegacyBaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.time.Instant
import java.time.LocalDate

@Entity
@Table(name = "popup", schema = "adevspoon")
class Popup(
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
    var openDate: LocalDate? = null,

    @Column(name = "close_date")
    var closeDate: LocalDate? = null
): LegacyBaseEntity()