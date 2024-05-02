package com.adevspoon.api.member.dto.response

import com.adevspoon.domain.member.dto.response.LikeInfo

data class MemberFavoriteListResponse(
    val list: List<MemberFavoriteResponse>,
    val next: String?
) {
    companion object {
        fun from(list: List<LikeInfo>, nextUrl: String?): MemberFavoriteListResponse {
            return MemberFavoriteListResponse(
                list = list.map { MemberFavoriteResponse.from(it) },
                next = nextUrl
            )
        }
    }
}