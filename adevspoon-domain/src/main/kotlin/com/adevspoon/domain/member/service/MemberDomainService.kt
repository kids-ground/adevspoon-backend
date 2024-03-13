package com.adevspoon.domain.member.service

import com.adevspoon.domain.member.domain.User
import com.adevspoon.domain.member.domain.UserActivity
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.domain.member.repository.UserActivityRepository
import com.adevspoon.domain.member.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberDomainService(
    private val userRepository: UserRepository,
    private val userActivityRepository: UserActivityRepository
) {
    // 유저정보와 회원가입 여부반환
    @Transactional
    fun getMemberAndIsSignUp(oAuth: UserOAuth, oAuthId: String): Pair<User, Boolean> {
        val user = when (oAuth) {
            UserOAuth.kakao -> userRepository.findByOAuthAndKakaoId(oAuth, oAuthId.toLong())
            UserOAuth.apple -> userRepository.findByOAuthAndAppleId(oAuth, oAuthId)
        }
        if (user != null) return user to false

        return when (oAuth) {
            UserOAuth.kakao -> User(oAuth = oAuth, kakaoId = oAuthId.toLong())
            UserOAuth.apple -> User(oAuth = oAuth, appleId = oAuthId)
        }.also {
            userRepository.save(it)
            userActivityRepository.save(UserActivity(id = it.id))
        } to true
    }
}