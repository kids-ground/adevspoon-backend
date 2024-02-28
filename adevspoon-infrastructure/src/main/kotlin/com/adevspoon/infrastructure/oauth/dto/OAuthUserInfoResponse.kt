package com.adevspoon.infrastructure.oauth.dto

import com.adevspoon.infrastructure.oauth.dto.OAuthType

data class OAuthUserInfoResponse(
    val type: OAuthType,
    val id: String,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val thumbnailImageUrl: String? = null,
    val email: String? = null,
)