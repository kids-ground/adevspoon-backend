package com.adevspoon.api.common.utils

import com.adevspoon.api.common.dto.JwtTokenInfo
import com.adevspoon.api.common.dto.JwtTokenType
import com.adevspoon.api.common.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.spec.SecretKeySpec

private const val USER_TOKEN_SUBJECT = "Token"
private const val USER_ID = "userId"
private const val USER_ROLE = "role"
private const val USER_TOKEN_TYPE = "tokenType"
@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    fun createToken(jwtTokenInfo: JwtTokenInfo): String = Jwts.builder()
        .setSubject(USER_TOKEN_SUBJECT)
        .claim(USER_ID, jwtTokenInfo.userId)
        .claim(USER_ROLE, jwtTokenInfo.authorities)
        .claim(USER_TOKEN_TYPE, jwtTokenInfo.tokenType)
        .setIssuer(jwtProperties.issuer)
        .setExpiration(
            Date.from(
                Instant.now().plus(
                    when (jwtTokenInfo.tokenType) {
                        JwtTokenType.ACCESS -> jwtProperties.accessExpirationHours
                        JwtTokenType.REFRESH -> jwtProperties.refreshExpirationHours
                    },
                    ChronoUnit.HOURS
                )
            )
        )
        .signWith(SecretKeySpec(jwtProperties.secretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName))
        .compact()!!

    fun validateTokenAndGetTokenInfo(token: String): JwtTokenInfo = Jwts.parserBuilder()
        .setSigningKey(jwtProperties.secretKey.toByteArray())
        .build()
        .parseClaimsJws(token)
        .body
        .let {
            JwtTokenInfo(
                userId = it.get(key = USER_ID) as Long,
                authorities = it.get(key = USER_ROLE) as String,
                tokenType = enumValueOf<JwtTokenType>(it.get(key = USER_TOKEN_TYPE) as String)
            )
        }
}