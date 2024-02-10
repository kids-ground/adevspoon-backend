package com.adevspoon.domain.post.board.domain

import com.adevspoon.domain.domain.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "BoardTag", schema = "adevspoon")
class BoardTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagId", nullable = false)
    val id: Int = 0,

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    val name: String? = null,

    @Size(max = 10)
    @NotNull
    @Column(name = "emoji", nullable = false, length = 10)
    val emoji: String? = null
): BaseEntity()