package com.adevspoon.domain.banner.domain

import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "banner_location", schema = "adevspoon")
class BannerLocation (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Size(max = 50)
    @Column(name = "name", length = 50)
    val name: String? = null
)