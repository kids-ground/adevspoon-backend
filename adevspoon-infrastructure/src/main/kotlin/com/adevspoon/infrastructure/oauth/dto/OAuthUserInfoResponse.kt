package com.adevspoon.infrastructure.oauth.dto

import com.adevspoon.infrastructure.oauth.dto.OAuthType

data class OAuthUserInfoResponse(
    val type: OAuthType,
    val id: String
)
