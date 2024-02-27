package com.adevspoon.domain.user.adapter

import com.adevspoon.domain.common.annotation.DomainAdapter
import com.adevspoon.domain.user.domain.UserActivity
import com.adevspoon.domain.user.repository.UserActivityRepository

@DomainAdapter
class UserActivityDomainAdapter(
    private val userActivityRepository: UserActivityRepository
) {
    fun save(userActivity: UserActivity) = userActivityRepository.save(userActivity)
}