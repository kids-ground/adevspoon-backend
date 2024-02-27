package com.adevspoon.domain.user.adapter

import com.adevspoon.domain.common.annotation.DomainAdapter
import com.adevspoon.domain.user.domain.User
import com.adevspoon.domain.user.domain.enums.UserOAuth
import com.adevspoon.domain.user.repository.UserRepository

@DomainAdapter
class UserDomainAdapter(
    private val userRepository: UserRepository
) {
    fun findByOAuthAndAppleId(oauthType: UserOAuth, socialId: String) = userRepository.findByOAuthAndAppleId(oauthType, socialId)

    fun findByOAuthAndKakaoId(oauthType: UserOAuth, socialId: Long) = userRepository.findByOAuthAndKakaoId(oauthType, socialId)

    fun save(user: User) = userRepository.save(user)
}