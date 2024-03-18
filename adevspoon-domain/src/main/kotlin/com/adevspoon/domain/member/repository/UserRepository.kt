package com.adevspoon.domain.member.repository

import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.domain.enums.UserOAuth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.oAuth = :oAuth AND u.appleId = :appleId AND u.status = 'active'")
    fun findByOAuthAndAppleId(oAuth: UserOAuth, appleId: String): UserEntity?

    @Query("SELECT u FROM UserEntity u WHERE u.oAuth = :oAuth AND u.kakaoId = :kakaoId AND u.status = 'active'")
    fun findByOAuthAndKakaoId(oAuth: UserOAuth, kakaoId: Long): UserEntity?
}