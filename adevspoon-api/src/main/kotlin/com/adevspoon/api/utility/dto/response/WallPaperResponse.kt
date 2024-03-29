package com.adevspoon.api.utility.dto.response

import com.adevspoon.api.common.dto.LegacyDtoEnum

data class WallPaperResponse(
    val id: Long,
    val type: WallPaperType,
    val content: String,
    val reference: String? = null
)

enum class WallPaperType: LegacyDtoEnum {
    QUOTE, MEME
}