package com.adevspoon.infrastructure.oauth.service

import com.adevspoon.infrastructure.oauth.client.AppleFeignClient
import com.adevspoon.infrastructure.oauth.dto.JwtHeader
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.*

private const val POSITIVE_SIGNUM: Int = 1

@Component
class AppleKeyService(
    val objectMapper: ObjectMapper,
    val appleFeignClient: AppleFeignClient,
) {
    fun getIdentityKey(identityToken: String): String {
        val publicKey = getPublicKey(identityToken)
        return validateAndGetKey(identityToken, publicKey)
    }

    private fun getPublicKey(identityToken: String): PublicKey {
        val jwtHeaderString = identityToken.split("\\.".toRegex()).toTypedArray()[0]
        val jwtHeader = objectMapper.readValue(jwtHeaderString, JwtHeader::class.java)

        return appleFeignClient.getAuthKeys()
            .keys
            .firstOrNull { key -> key.alg == jwtHeader.alg && key.kid == jwtHeader.kid }
            ?.let {
                val decodedN = Base64.getDecoder().decode(it.n)
                val decodedE = Base64.getDecoder().decode(it.e)
                val publicKeySpec = RSAPublicKeySpec(BigInteger(POSITIVE_SIGNUM, decodedN), BigInteger(POSITIVE_SIGNUM, decodedE))
                val keyFactory = KeyFactory.getInstance(it.kty)
                keyFactory.generatePublic(publicKeySpec)
            }
            ?: throw IllegalArgumentException("publicKey is null")
    }

    private fun validateAndGetKey(identityToken: String, publicKey: PublicKey): String {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(identityToken)
                .body["sub"] as String
        } catch (e: Exception) {
            throw IllegalArgumentException("invalid identityToken")
        }
    }
}