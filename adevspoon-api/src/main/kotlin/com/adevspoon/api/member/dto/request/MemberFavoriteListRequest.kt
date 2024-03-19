package com.adevspoon.api.member.dto.request

data class MemberFavoriteListRequest(
    // TODO: Cursor 기반 페이지네이션
    val type: FavoriteType,
    val startId: Long,
    val take: Long,
)
