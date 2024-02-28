package com.adevspoon.infrastructure.oauth.service

import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoResponse

interface OAuthAdapter {
    fun getOAuthUserInfo(oAuthUserInfoRequest: OAuthUserInfoRequest): OAuthUserInfoResponse
}