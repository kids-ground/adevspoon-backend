package com.adevspoon.domain.board.dto.request

import com.adevspoon.domain.common.entity.enums.ReportReason

data class CreateReportRequest(
    val type: Any,
    val contentId: Long,
    val reason: ReportReason
)
