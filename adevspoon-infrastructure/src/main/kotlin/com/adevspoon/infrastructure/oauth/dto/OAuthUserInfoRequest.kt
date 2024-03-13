package com.adevspoon.infrastructure.oauth.dto

import com.adevspoon.common.enums.SocialType

data class OAuthUserInfoRequest(
    val type: SocialType,
    val kakaoAccessToken: String? = null,
    val appleIdentityToken: String? = null
)
