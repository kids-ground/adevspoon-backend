package com.adevspoon.domain.utility.domain.enums

import com.adevspoon.domain.common.entity.LegacyEntityEnum
import com.adevspoon.domain.common.converter.LegacyEntityEnumConverter
import jakarta.persistence.Converter

enum class BannerLocationType: LegacyEntityEnum {
    HOME;

    @Converter(autoApply = true)
    class LegacyConverter: LegacyEntityEnumConverter<BannerLocationType>(BannerLocationType::class.java, nullable = false)
}