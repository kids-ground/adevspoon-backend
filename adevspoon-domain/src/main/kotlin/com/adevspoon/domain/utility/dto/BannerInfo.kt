package com.adevspoon.domain.utility.dto

import com.adevspoon.domain.utility.domain.BannerEntity

data class BannerInfo(
    val bannerId: Long,
    val imageUrl: String,
    val url: String,
) {
    companion object {
        fun from(banner: BannerEntity): BannerInfo {
            return BannerInfo(
                bannerId = banner.id,
                imageUrl = banner.imageUrl ?: "",
                url = banner.url ?: "",
            )
        }
    }
}
