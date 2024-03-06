package com.adevspoon.infrastructure.oauth.service

import com.adevspoon.infrastructure.oauth.client.KakaoFeignClient
import com.adevspoon.infrastructure.oauth.dto.KakaoUserInfoResponse
import com.adevspoon.infrastructure.oauth.dto.OAuthType
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows

class OAuthAdapterImplTest {

    private val kakaoFeignClient = mockk<KakaoFeignClient>()
    private val appleKeyService = mockk<AppleKeyService>()

    private lateinit var oAuthAdapterImpl: OAuthAdapterImpl

    @BeforeEach
    fun setUp() {
        oAuthAdapterImpl = OAuthAdapterImpl(kakaoFeignClient, appleKeyService)
    }

    @Nested
    inner class getOAuthUserInfo {
        @Test
        fun `SUCCESS - 카카오 로그인 성공`() {
            every { kakaoFeignClient.getUserProfile(any()) } returns KakaoUserInfoResponse(
                id = 1234,
                kakaoAccount = KakaoUserInfoResponse.KakaoAccount(
                    profileNicknameNeedsAgreement = false,
                    profile = KakaoUserInfoResponse.KakaoAccount.KakaoProfile(
                        nickname = "nickname",
                        thumbnailImageUrl = "thumbnailImageUrl",
                        profileImageUrl = "profileImageUrl",
                        isDefaultImage = false
                    ),
                    email = ""
                )
            )
            val oAuthUserInfo = oAuthAdapterImpl.getOAuthUserInfo(
                OAuthUserInfoRequest(
                    OAuthType.KAKAO,
                    kakaoAccessToken = "accessToken"
                )
            )

            assertEquals(oAuthUserInfo.id, "1234")
            verify { kakaoFeignClient.getUserProfile(any()) }
            verify { appleKeyService wasNot Called }
        }

        @Test
        fun `SUCCESS - 애플 로그인 성공`() {
            every { appleKeyService.getIdentityKey(any()) } returns "appleId"

            val oAuthUserInfo = oAuthAdapterImpl.getOAuthUserInfo(
                OAuthUserInfoRequest(
                    OAuthType.APPLE,
                    appleIdentityToken = "identityToken"
                )
            )

            assertEquals(oAuthUserInfo.id, "appleId")
            verify { appleKeyService.getIdentityKey(any()) }
            verify { kakaoFeignClient wasNot Called }
        }

        @Test
        fun `FAIL - 카카오 로그인 잘못된 AccessToken`() {
            assertThrows<RuntimeException> {
                oAuthAdapterImpl.getOAuthUserInfo(OAuthUserInfoRequest(OAuthType.KAKAO, null))
            }
            verify { kakaoFeignClient wasNot Called }
            verify { appleKeyService wasNot Called }
        }

        @Test
        fun `FAIL - 애플 로그인 잘못된 IdentityToken`() {
            assertThrows<RuntimeException> {
                oAuthAdapterImpl.getOAuthUserInfo(OAuthUserInfoRequest(OAuthType.APPLE, null))
            }
            verify { kakaoFeignClient wasNot Called }
            verify { appleKeyService wasNot Called }
        }
    }
}