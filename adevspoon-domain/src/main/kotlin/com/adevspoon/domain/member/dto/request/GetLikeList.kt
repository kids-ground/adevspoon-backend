package com.adevspoon.domain.member.dto.request

data class GetLikeList(
    val requestMemberId: Long,
    val startId: Long?,
    val take: Int,
)
