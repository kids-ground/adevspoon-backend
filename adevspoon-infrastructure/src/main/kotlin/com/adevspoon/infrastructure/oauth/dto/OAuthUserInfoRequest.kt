package com.adevspoon.infrastructure.oauth.dto

import com.adevspoon.infrastructure.oauth.dto.OAuthType

data class OAuthUserInfoRequest(
    val type: OAuthType,
    val token: String?
)
