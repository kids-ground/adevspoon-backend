package com.adevspoon.domain.member.domain.enums

import com.adevspoon.domain.common.entity.LegacyEntityEnum
import com.adevspoon.domain.config.converter.LegacyEntityEnumConverter
import jakarta.persistence.Converter

enum class UserProfileBelt: LegacyEntityEnum {
    NONE, PASSIONATE;

    @Converter(autoApply = true)
    class LegacyConverter: LegacyEntityEnumConverter<UserProfileBelt>(UserProfileBelt::class.java)
}
