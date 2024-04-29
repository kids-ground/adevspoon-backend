package com.adevspoon.domain.utility.repository

import com.adevspoon.domain.utility.domain.PopupEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PopupRepository: JpaRepository<PopupEntity, Long> {
}