package com.adevspoon.domain.member.domain.enums

import com.adevspoon.common.enums.SocialType

enum class UserOAuth {
    kakao, apple;

    companion object {
        fun from(type: SocialType): UserOAuth {
            return valueOf(type.name.lowercase())
        }
    }
}