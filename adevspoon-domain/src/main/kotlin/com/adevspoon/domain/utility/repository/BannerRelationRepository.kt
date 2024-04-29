package com.adevspoon.domain.utility.repository

import com.adevspoon.domain.utility.domain.BannerLocationRelationEntity
import com.adevspoon.domain.utility.domain.BannerLocationRelationId
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRelationRepository: JpaRepository<BannerLocationRelationEntity, BannerLocationRelationId> {
}