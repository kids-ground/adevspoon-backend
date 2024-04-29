package com.adevspoon.domain.utility.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.utility.dto.BannerInfo
import com.adevspoon.domain.utility.dto.PopupInfo
import com.adevspoon.domain.utility.dto.WallpaperInfo
import com.adevspoon.domain.utility.exception.PopupNotSupportedException
import com.adevspoon.domain.utility.repository.BannerRepository
import com.adevspoon.domain.utility.repository.WallpaperRepository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@DomainService
class UtilityDomainService(
    private val wallpaperRepository: WallpaperRepository,
    private val bannerRepository: BannerRepository,
) {
    @Transactional(readOnly = true)
    fun getWallPaper(): WallpaperInfo {
        return WallpaperInfo.from(
            wallpaperRepository.findAll().random()
        )
    }

    fun getPopup(): PopupInfo {
        throw PopupNotSupportedException()
    }

    fun getBannerList(): List<BannerInfo> {
        return bannerRepository.findAllExistBanner(LocalDate.now())
            .map { BannerInfo.from(it) }
    }
}