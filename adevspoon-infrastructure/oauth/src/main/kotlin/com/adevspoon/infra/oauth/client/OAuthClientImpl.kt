package com.adevspoon.infra.oauth.client

import com.adevspoon.domain.oauthcore.client.OAuthClient
import com.adevspoon.domain.oauthcore.dto.OAuthUserInfoRequest
import com.adevspoon.domain.oauthcore.dto.OAuthUserInfoResponse
import com.adevspoon.infra.oauth.internal.AppleFeignClient
import com.adevspoon.infra.oauth.internal.KakaoFeignClient
import org.springframework.stereotype.Component

@Component
class OAuthClientImpl(
    private val kakaoFeignClient: KakaoFeignClient,
    private val appleFeignClient: AppleFeignClient,
): OAuthClient {
    override fun getOAuthUserInfo(oAuthUserInfoRequest: OAuthUserInfoRequest): OAuthUserInfoResponse {
        TODO("Not yet implemented")
    }
}