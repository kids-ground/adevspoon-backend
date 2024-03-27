package com.adevspoon.api.utility.dto.response

data class WallPaperResponse(
    val id: Long,
    val type: WallPaperType,
    val content: String,
    val reference: String? = null
)

enum class WallPaperType {
    QUOTE, MEME
}