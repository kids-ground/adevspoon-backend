package com.adevspoon.domain.utility.repository

import com.adevspoon.domain.utility.domain.WallpaperEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WallpaperRepository: JpaRepository<WallpaperEntity, Long> {
}