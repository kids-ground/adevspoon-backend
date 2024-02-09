package com.adevspoon.domain.oauthcore.dto

import com.adevspoon.domain.oauthcore.dto.OAuthType

data class OAuthUserInfoRequest(
    val type: OAuthType,
    val token: String?
)
