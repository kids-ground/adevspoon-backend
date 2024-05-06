package com.adevspoon.domain.member.dto.request

data class GetActivityRequest(
    val userId: Long,
    val month: Int,
    val year: Int
)
