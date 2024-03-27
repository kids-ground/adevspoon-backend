package com.adevspoon.domain.member.domain

import com.adevspoon.domain.config.converter.AuthRoleToStringConverter
import com.adevspoon.domain.member.domain.enums.AuthRole
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "auth_group", schema = "adevspoon")
class AuthGroupEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Size(max = 25)
    @NotNull
    @Column(name = "auth_group_name", nullable = false, length = 25)
    @Convert(converter = AuthRoleToStringConverter::class)
    val authGroupName: AuthRole
)