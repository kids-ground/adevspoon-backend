package com.adevspoon.domain.utility.repository

import com.adevspoon.domain.utility.domain.BannerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface BannerRepository: JpaRepository<BannerEntity, Long> {

    @Query("SELECT b " +
            "FROM BannerEntity b " +
            "WHERE Date(b.openDate) <= :nowDate " +
            "AND Date(b.closeDate) >= :nowDate OR b.closeDate IS NULL " +
            "ORDER BY b.openDate DESC ")
    fun findAllExistBanner(nowDate: LocalDate): List<BannerEntity>
}