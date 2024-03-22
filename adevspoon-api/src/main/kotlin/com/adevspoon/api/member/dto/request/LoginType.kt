package com.adevspoon.api.member.dto.request

import com.adevspoon.api.common.dto.LegacyDtoEnum
import com.adevspoon.common.enums.SocialType

enum class LoginType: LegacyDtoEnum {
    KAKAO,
    APPLE;

    fun toSocialType(): SocialType {
        return enumValueOf(this.name)
    }
}