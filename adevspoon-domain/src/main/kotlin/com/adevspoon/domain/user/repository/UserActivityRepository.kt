package com.adevspoon.domain.user.repository

import com.adevspoon.domain.user.domain.UserActivity
import org.springframework.data.jpa.repository.JpaRepository

interface UserActivityRepository : JpaRepository<UserActivity, Long> {
}