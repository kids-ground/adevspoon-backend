package com.adevspoon.domain.dbcore.user.service

import com.adevspoon.domain.dbcore.user.domain.User
import com.adevspoon.domain.dbcore.user.domain.UserActivity
import com.adevspoon.domain.dbcore.user.domain.enums.UserOAuth
import com.adevspoon.domain.dbcore.user.repository.UserActivityRepository
import com.adevspoon.domain.dbcore.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userActivityRepository: UserActivityRepository
) {
    @Transactional
    fun getOrCreateUser(socialId: String, oauthType: UserOAuth): User {
        val user = when (oauthType) {
            UserOAuth.kakao -> userRepository.findByOAuthAndAppleId(oauthType, socialId)
            UserOAuth.apple -> userRepository.findByOAuthAndKakaoId(oauthType, socialId.toLong())
        }

        return user ?: createUser(socialId, oauthType)
    }

    private fun createUser(socialId: String, oauthType: UserOAuth): User {
        val user = when (oauthType) {
            UserOAuth.kakao -> User(oAuth = oauthType, kakaoId = socialId.toLong())
            UserOAuth.apple -> User(oAuth = oauthType, appleId = socialId)
        }
        val userActivity = UserActivity(id = user.id)

        userRepository.save(user)
        userActivityRepository.save(userActivity)

        return user
    }
}