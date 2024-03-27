package com.adevspoon.domain.member.domain.enums

import com.adevspoon.common.enums.SocialType
import com.adevspoon.domain.common.entity.LegacyEntityEnum
import com.adevspoon.domain.config.converter.LegacyEntityEnumConverter
import jakarta.persistence.Converter

enum class UserOAuth: LegacyEntityEnum {
    KAKAO, APPLE;

    companion object {
        fun from(type: SocialType): UserOAuth {
            return valueOf(type.name)
        }
    }

    @Converter(autoApply = true)
    class LegacyConverter: LegacyEntityEnumConverter<UserOAuth>(UserOAuth::class.java, true)
}