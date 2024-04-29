package com.adevspoon.domain.utility.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "Wallpaper", schema = "adevspoon")
class WallpaperEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Size(max = 5)
    @Column(name = "type", length = 5, columnDefinition = "char(5)")
    val type: String? = null,

    @Size(max = 50)
    @NotNull
    @Column(name = "content", nullable = false, length = 50)
    val content: String,

    @Size(max = 255)
    @Column(name = "reference")
    val reference: String? = null,
)