package com.adevspoon.infrastructure.oauth.service

import com.adevspoon.common.enums.SocialType
import com.adevspoon.infrastructure.common.annotation.Adapter
import com.adevspoon.infrastructure.oauth.client.KakaoFeignClient
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import com.adevspoon.common.dto.OAuthUserInfo
import com.adevspoon.infrastructure.oauth.exception.OAuthErrorCode

@Adapter
class OAuthAdapterImpl(
    private val kakaoFeignClient: KakaoFeignClient,
    private val appleKeyService: AppleKeyService,
): OAuthAdapter {
    override fun getOAuthUserInfo(oAuthUserInfoRequest: OAuthUserInfoRequest): OAuthUserInfo =
        when (oAuthUserInfoRequest.type) {
            SocialType.KAKAO -> getKakaoUserInfo(oAuthUserInfoRequest.kakaoAccessToken)
            SocialType.APPLE -> getAppleUserInfo(oAuthUserInfoRequest.appleIdentityToken)
        }

    private fun getKakaoUserInfo(accessToken: String?): OAuthUserInfo =
        accessToken?.let {
            val kakaoResponse = kakaoFeignClient.getUserProfile("Bearer $accessToken")
            OAuthUserInfo(
                id = "${kakaoResponse.id}",
                type = SocialType.KAKAO,
                email = kakaoResponse.kakaoAccount.email,
                nickname = kakaoResponse.kakaoAccount.profile.nickname,
                profileImageUrl = kakaoResponse.kakaoAccount.profile.profileImageUrl,
                thumbnailImageUrl = kakaoResponse.kakaoAccount.profile.thumbnailImageUrl,
            )
        } ?: throw OAuthErrorCode.KAKAO_TOKEN_EMPTY.getException()

    private fun getAppleUserInfo(identityToken: String?): OAuthUserInfo =
        identityToken?.let {
            OAuthUserInfo(
                id = appleKeyService.getIdentityKey(it),
                type = SocialType.APPLE
            )
        } ?: throw OAuthErrorCode.APPLE_TOKEN_EMPTY.getException()
}