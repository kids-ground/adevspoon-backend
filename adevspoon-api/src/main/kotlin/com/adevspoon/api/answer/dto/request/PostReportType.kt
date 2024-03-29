package com.adevspoon.api.answer.dto.request

import com.adevspoon.api.common.dto.LegacyDtoEnum

enum class PostReportType: LegacyDtoEnum {
    ABUSE,
    SPAMMER,
    OBSCENE,
    SCAM,
    POLITICAL_AGITATION,
    ILLEGAL_ADS_AND_SALES,
    ETC,
    NONE
}