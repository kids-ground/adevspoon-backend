package com.adevspoon.domain.utility.dto

import com.adevspoon.domain.utility.domain.WallpaperEntity

data class WallpaperInfo(
    val id: Long,
    val content: String,
    val reference: String
) {
    companion object {
        fun from(wallpaper: WallpaperEntity) = WallpaperInfo(
            id = wallpaper.id,
            content = wallpaper.content,
            reference = wallpaper.reference ?: ""
        )
    }
}
