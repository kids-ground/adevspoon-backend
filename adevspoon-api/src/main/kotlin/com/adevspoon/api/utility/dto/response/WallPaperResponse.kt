package com.adevspoon.api.utility.dto.response

import com.adevspoon.api.common.dto.LegacyDtoEnum
import com.adevspoon.domain.utility.dto.WallpaperInfo

data class WallPaperResponse(
    val id: Long,
    val type: WallPaperType = WallPaperType.QUOTE,
    val content: String,
    val reference: String? = null
) {
    companion object {
        fun from(info: WallpaperInfo) = WallPaperResponse(
            id = info.id,
            content = info.content,
            reference = info.reference
        )
    }
}

enum class WallPaperType: LegacyDtoEnum {
    QUOTE
}