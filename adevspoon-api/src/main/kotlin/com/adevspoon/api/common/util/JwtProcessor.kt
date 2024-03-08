package com.adevspoon.api.common.util

import com.adevspoon.api.common.dto.JwtTokenInfo
import com.adevspoon.api.common.dto.JwtTokenType
import com.adevspoon.api.common.enums.ServiceRole
import com.adevspoon.api.common.properties.JwtProperties
import com.adevspoon.common.exception.CommonErrorCode
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.spec.SecretKeySpec

private const val USER_TOKEN_SUBJECT = "Token"
private const val USER_ID = "user_id"
private const val USER_ROLE = "role"
private const val USER_TOKEN_TYPE = "tokenType"

@Component
class JwtProcessor(
    private val jwtProperties: JwtProperties
) {
    fun createToken(jwtTokenInfo: JwtTokenInfo): String = Jwts.builder()
        .claim(USER_ID, jwtTokenInfo.userId)
        .claim(USER_ROLE, jwtTokenInfo.role.name)
        .claim(USER_TOKEN_TYPE, jwtTokenInfo.tokenType)
        .setSubject(USER_TOKEN_SUBJECT)
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

    fun validateAuthorizationHeader(authorizationHeader: String): JwtTokenInfo = try {
        val token = authorizationHeader.removePrefix("Bearer ")

        Jwts.parserBuilder()
            .setSigningKey(jwtProperties.secretKey.toByteArray())
            .build()
            .parseClaimsJws(token)
            .body
            .let {
                JwtTokenInfo(
                    userId = (it.get(key = USER_ID) as? Int)?.toLong() ?: 0L,
                    role = enumValueOf(it.get(key = USER_ROLE) as? String? ?: ServiceRole.USER.name),
                    tokenType = enumValueOf(it.get(key = USER_TOKEN_TYPE) as? String? ?: JwtTokenType.ACCESS.name)
                )
            }
    } catch (e: Exception) {
        throw CommonErrorCode.UNAUTHORIZED.getException()
    }
}