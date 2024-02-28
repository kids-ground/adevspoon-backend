package com.adevspoon.domain.member.repository

import com.adevspoon.domain.member.domain.UserActivity
import org.springframework.data.jpa.repository.JpaRepository

interface UserActivityRepository : JpaRepository<UserActivity, Long> {
}