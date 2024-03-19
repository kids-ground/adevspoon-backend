package com.adevspoon.api.wallPaper.dto

data class WallPaperResponse(
    val id: Long,
    val type: WallPaperType,
    val content: String,
    val reference: String? = null
)

enum class WallPaperType {
    QUOTE, MEME
}