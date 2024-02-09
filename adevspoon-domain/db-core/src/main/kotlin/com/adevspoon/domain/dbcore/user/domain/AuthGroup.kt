package com.adevspoon.domain.dbcore.user.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "auth_group", schema = "adevspoon")
class AuthGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Size(max = 25)
    @NotNull
    @Column(name = "auth_group_name", nullable = false, length = 25)
    val authGroupName: String? = null
)