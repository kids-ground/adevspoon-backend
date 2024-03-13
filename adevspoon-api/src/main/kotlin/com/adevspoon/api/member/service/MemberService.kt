package com.adevspoon.api.member.service

import com.adevspoon.domain.member.domain.User
import com.adevspoon.domain.member.service.MemberDomainService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberDomainService: MemberDomainService
) {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Transactional
    fun updateProfile(userId: Long): User {
        TODO("User get - User update(Profile 인자에 담긴 정보 모두)")
    }
}