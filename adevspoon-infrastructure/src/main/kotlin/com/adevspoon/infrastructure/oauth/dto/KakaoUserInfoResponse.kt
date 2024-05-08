package com.adevspoon.infrastructure.oauth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserInfoResponse(
    val id: Long,
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount
) {
    data class KakaoAccount (
        @JsonProperty("profile_nickname_needs_agreement")
        val profileNicknameNeedsAgreement: Boolean?,
        val profile: KakaoProfile,
        val email: String?,
    ) {
        data class KakaoProfile(
            val nickname: String?,

            @JsonProperty("thumbnail_image_url")
            val thumbnailImageUrl: String?,

            @JsonProperty("profile_image_url")
            val profileImageUrl: String?,

            @JsonProperty("is_default_image")
            val isDefaultImage: Boolean?,
        )
    }
}
