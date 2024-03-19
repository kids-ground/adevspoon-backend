package com.adevspoon.api.member.dto.response

data class MemberFavoriteListResponse(
    val list: List<MemberFavoriteResponse>,
    val next: String?
)