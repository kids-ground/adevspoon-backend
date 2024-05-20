package com.adevspoon.domain.common.event

import com.adevspoon.domain.common.entity.ReportEntity
import java.time.LocalDateTime

data class ReportEvent(
    val content: String,
    val report: ReportEntity
) {
    fun toSpecificMap(): Map<String, Any> {
        return mapOf(
            "Report Time" to LocalDateTime.now().toString(),
            "Report Reason" to report.reason.toString(),
            "Report Type" to report.postType,
            "Report (id: $content)" to content,
            "Reported by" to "id: ${report.user.id}, name: ${report.user.nickname}"
        )
    }
}