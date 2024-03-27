package com.adevspoon.domain.access.domain.enums

import com.adevspoon.domain.common.entity.LegacyEntityEnum
import com.adevspoon.domain.common.converter.LegacyEntityEnumConverter
import jakarta.persistence.Converter

enum class AccessLogType: LegacyEntityEnum {
    BOARD_POST;

    @Converter(autoApply = true)
    class LegacyConverter: LegacyEntityEnumConverter<AccessLogType>(AccessLogType::class.java, false)
}