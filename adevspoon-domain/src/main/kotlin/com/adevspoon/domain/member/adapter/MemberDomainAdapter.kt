package com.adevspoon.domain.member.adapter

import com.adevspoon.domain.common.annotation.DomainAdapter
import com.adevspoon.domain.member.domain.User
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.domain.member.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull

@DomainAdapter
class MemberDomainAdapter(
    private val userRepository: UserRepository
) {
    fun findByOAuthAndAppleId(oauthType: UserOAuth, socialId: String): User? = userRepository.findByOAuthAndAppleId(oauthType, socialId)
    fun findByOAuthAndKakaoId(oauthType: UserOAuth, socialId: Long): User? = userRepository.findByOAuthAndKakaoId(oauthType, socialId)
    fun save(user: User) = userRepository.save(user)
    fun findByUserId(userId: Long) = userRepository.findByIdOrNull(userId)
}