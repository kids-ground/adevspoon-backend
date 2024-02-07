
package com.adevspoon.common.jwt

import com.adevspoon.common.jwt.dto.JwtTokenInfo
import com.adevspoon.common.jwt.properties.JwtProperties
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

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    fun createUserToken(jwtTokenInfo: JwtTokenInfo): String = Jwts.builder()
        .setSubject(USER_TOKEN_SUBJECT)
        .claim(USER_ID, jwtTokenInfo.userId)
        .claim(USER_ROLE, jwtTokenInfo.authorities)
        .setIssuer(jwtProperties.issuer)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(jwtProperties.expirationHours, ChronoUnit.HOURS)))
        .signWith(SecretKeySpec(jwtProperties.secretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName))
        .compact()!!

    fun validateUserTokenAndGetSubject(token: String): JwtTokenInfo = Jwts.parserBuilder()
        .setSigningKey(jwtProperties.secretKey.toByteArray())
        .build()
        .parseClaimsJws(token)
        .body
        .let {
            JwtTokenInfo(
                userId = it.get(key = USER_ID) as Long,
                authorities = it.get(key = USER_ROLE) as String
            )
        }
}