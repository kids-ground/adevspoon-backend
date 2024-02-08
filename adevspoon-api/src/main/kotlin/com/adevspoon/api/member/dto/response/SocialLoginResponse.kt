package com.adevspoon.api.member.dto.response

import java.time.LocalDateTime

data class SocialLoginResponse (
    val token: ServiceToken,
    val isSign: Boolean,
    val user_id: Long,
    val nickname: String,
    val profile_img: String?,
    val thumbnail_img: String?,
    val question_cnt: Int,
    val answer_cnt: Int,
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime,
    val alarm_on: Boolean,
)

data class ServiceToken (
    val accessToken: String,
    val refreshToken: String,
)