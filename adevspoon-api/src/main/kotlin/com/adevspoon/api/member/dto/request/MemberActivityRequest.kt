package com.adevspoon.api.member.dto.request

data class MemberActivityRequest(
    val userId: Long,
    val month: Int,
    val year: Int,
)
