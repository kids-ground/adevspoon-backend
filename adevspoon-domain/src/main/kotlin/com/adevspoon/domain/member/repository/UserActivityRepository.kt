package com.adevspoon.domain.member.repository

import com.adevspoon.domain.member.domain.UserActivityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserActivityRepository : JpaRepository<UserActivityEntity, Long> {
}