package com.adevspoon.infrastructure.oauth.dto

import com.adevspoon.common.enums.SocialType

data class OAuthUserInfoResponse(
    val type: SocialType,
    val id: String,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val thumbnailImageUrl: String? = null,
    val email: String? = null,
)