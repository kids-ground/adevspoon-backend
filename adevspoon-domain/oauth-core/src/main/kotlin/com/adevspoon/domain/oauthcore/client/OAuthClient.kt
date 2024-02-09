package com.adevspoon.domain.oauthcore.client

import com.adevspoon.domain.oauthcore.dto.OAuthUserInfoRequest
import com.adevspoon.domain.oauthcore.dto.OAuthUserInfoResponse

interface OAuthClient {
    fun getOAuthUserInfo(oAuthUserInfoRequest: OAuthUserInfoRequest): OAuthUserInfoResponse
}