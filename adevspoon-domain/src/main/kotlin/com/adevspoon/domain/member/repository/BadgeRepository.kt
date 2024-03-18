package com.adevspoon.domain.member.repository

import com.adevspoon.domain.member.domain.BadgeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BadgeRepository: JpaRepository<BadgeEntity, Long> {
}