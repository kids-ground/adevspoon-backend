package com.adevspoon.api.utility.dto.response

import com.adevspoon.domain.utility.dto.PopupInfo
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class PopupResponse(
    val id: Long,
    val name: String? = null,
    val imageUrl: String,
    @Schema(description = "팝 클릭했을 때 이동할 url")
    val url: String? = null,
    @Schema(description = "팝업 오픈 날짜", example = "2024-01-01")
    val openDate: LocalDate,
    @Schema(description = "팝업 종료 날짜", example = "2024-01-10")
    val closeDate: LocalDate? = null
) {
    companion object {
        fun from(info: PopupInfo) = PopupResponse(
            id = info.id,
            name = info.name,
            imageUrl = info.imageUrl,
            url = info.url,
            openDate = info.openDate,
            closeDate = info.closeDate
        )
    }
}
