package com.adevspoon.domain.utility.domain

import com.adevspoon.domain.utility.domain.enums.BannerLocationType
import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "banner_location", schema = "adevspoon")
class BannerLocationEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Size(max = 50)
    @Column(name = "name", length = 50)
    val name: BannerLocationType = BannerLocationType.HOME,
)