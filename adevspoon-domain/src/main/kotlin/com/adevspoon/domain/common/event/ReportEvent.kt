package com.adevspoon.domain.common.event

import com.adevspoon.domain.common.entity.ReportEntity

data class ReportEvent(
    val content: String,
    val report: ReportEntity
)