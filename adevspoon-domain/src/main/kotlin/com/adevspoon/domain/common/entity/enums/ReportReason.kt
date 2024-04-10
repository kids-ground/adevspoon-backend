package com.adevspoon.domain.common.entity.enums

import com.adevspoon.domain.common.converter.LegacyEntityEnumConverter
import com.adevspoon.domain.common.entity.LegacyEntityEnum
import jakarta.persistence.Converter

enum class ReportReason: LegacyEntityEnum {
    ABUSE, SPAMMER, OBSCENE, SCAM, POLITICAL_AGITATION, ILLEGAL_ADS_AND_SALES, ETC;

    @Converter(autoApply = true)
    class ReportReasonConverter: LegacyEntityEnumConverter<ReportReason>(ReportReason::class.java, nullable = true)
}