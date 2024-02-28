package com.adevspoon.domain.member.adapter

import com.adevspoon.domain.common.annotation.DomainAdapter
import com.adevspoon.domain.member.domain.UserActivity
import com.adevspoon.domain.member.repository.UserActivityRepository

@DomainAdapter
class MemberActivityDomainAdapter(
    private val userActivityRepository: UserActivityRepository
) {
    fun save(userActivity: UserActivity) = userActivityRepository.save(userActivity)
}