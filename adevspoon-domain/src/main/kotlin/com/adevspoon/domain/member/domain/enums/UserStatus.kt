package com.adevspoon.domain.member.domain.enums

import com.adevspoon.domain.common.entity.LegacyEntityEnum
import com.adevspoon.domain.common.converter.LegacyEntityEnumConverter
import jakarta.persistence.Converter

enum class UserStatus: LegacyEntityEnum {
    ACTIVE, EXIT;

    @Converter(autoApply = true)
    class LegacyConverter: LegacyEntityEnumConverter<UserStatus>(UserStatus::class.java, true)
}