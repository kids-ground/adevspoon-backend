package com.adevspoon.domain.oauth.client

import com.adevspoon.domain.oauth.dto.OAuthUserInfoRequest
import com.adevspoon.domain.oauth.dto.OAuthUserInfoResponse

interface OAuthClient {
    fun getOAuthUserInfo(oAuthUserInfoRequest: OAuthUserInfoRequest): OAuthUserInfoResponse
}