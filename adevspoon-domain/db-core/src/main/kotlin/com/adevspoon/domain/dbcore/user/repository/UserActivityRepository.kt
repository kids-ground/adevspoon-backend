package com.adevspoon.domain.dbcore.user.repository

import com.adevspoon.domain.dbcore.user.domain.UserActivity
import org.springframework.data.jpa.repository.JpaRepository

interface UserActivityRepository : JpaRepository<UserActivity, Long> {
}