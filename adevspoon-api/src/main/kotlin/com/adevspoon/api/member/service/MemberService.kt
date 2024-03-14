package com.adevspoon.api.member.service

import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.api.member.dto.request.MemberProfileUpdateRequest
import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.adevspoon.domain.member.service.MemberDomainService
import org.slf4j.LoggerFactory

@ApplicationService
class MemberService(
    private val memberDomainService: MemberDomainService
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)!!

    fun updateProfile(userId: Long, request: MemberProfileUpdateRequest): MemberProfileResponse {
        TODO("""
            1. 
        """.trimIndent())
    }
}