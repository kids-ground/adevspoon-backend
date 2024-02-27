package com.adevspoon.api.member.service

import com.adevspoon.api.member.dto.request.SocialLoginType
import com.adevspoon.api.member.dto.response.SocialLoginResponse
import com.adevspoon.domain.common.annotation.DomainAdapter
import com.adevspoon.domain.user.adapter.UserActivityDomainAdapter
import com.adevspoon.domain.user.adapter.UserDomainAdapter
import com.adevspoon.domain.user.domain.User
import com.adevspoon.domain.user.domain.UserActivity
import com.adevspoon.domain.user.domain.enums.UserOAuth
import com.adevspoon.domain.user.repository.UserActivityRepository
import com.adevspoon.domain.user.repository.UserRepository
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val userDomainAdapter: UserDomainAdapter,
    private val userActivityDomainAdapter: UserActivityDomainAdapter,
) {

    @Transactional
    fun getOrCreateUser(socialId: String, oauthType: UserOAuth): SocialLoginResponse =
        when (oauthType) {
            UserOAuth.kakao -> userDomainAdapter.findByOAuthAndAppleId(oauthType, socialId)
            UserOAuth.apple -> userDomainAdapter.findByOAuthAndKakaoId(oauthType, socialId.toLong())
        }?.let {
            SocialLoginResponse.from(it, false)
        } ?: createUser(socialId, oauthType)

    private fun createUser(socialId: String, oauthType: UserOAuth): SocialLoginResponse {
        val user = when (oauthType) {
            UserOAuth.kakao -> User(oAuth = oauthType, kakaoId = socialId.toLong())
            UserOAuth.apple -> User(oAuth = oauthType, appleId = socialId)
        }
        // user 기본값 추가하기

        userDomainAdapter.save(user)

        val userActivity = UserActivity(id = user.id)
        userActivityDomainAdapter.save(userActivity)

        return SocialLoginResponse.from(user, true)
    }

    private fun createRandomNickname(): String {
        val adjectives = listOf("Happy", "Sad", "Angry", "Excited", "Bored", "Tired", "Sleepy", "Hungry", "Thirsty", "Silly")
        val animals = listOf("Dog", "Cat", "Bird", "Fish", "Turtle", "Rabbit", "Hamster", "Horse", "Pig", "Cow")
        return "${adjectives.random()}${animals.random()}"
    }
}