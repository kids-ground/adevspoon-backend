package com.adevspoon.api.board.dto.request

import com.adevspoon.api.answer.dto.request.PostReportType

data class ReportBoardContentRequest(
    val type: BoardContentType,
    val contentId: Long,
    val reason: PostReportType,
)