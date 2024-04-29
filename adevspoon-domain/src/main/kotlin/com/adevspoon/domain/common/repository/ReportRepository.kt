package com.adevspoon.domain.common.repository

import com.adevspoon.domain.common.entity.ReportEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository : JpaRepository<ReportEntity, Long>
