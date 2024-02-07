
package com.adevspoon.common.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.spec.SecretKeySpec

private const val USER_TOKEN_SUBJECT = "TOKEN"

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    // TODO - JWT Body 유저정보 추가 필요 - userId, Role 등
    fun createUserToken(): String = Jwts.builder()
        .signWith(SecretKeySpec(jwtProperties.secretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName))
        .setSubject(USER_TOKEN_SUBJECT)
        .setIssuer(jwtProperties.issuer)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(jwtProperties.expirationHours, ChronoUnit.HOURS)))
        .compact()!!

    // TODO - JWT Body 파싱해서 유저정보 추출 필요
    fun validateUserTokenAndGetSubject(token: String): String = Jwts.parserBuilder()
        .setSigningKey(jwtProperties.secretKey.toByteArray())
        .build()
        .parseClaimsJws(token)
        .body
        .subject
}