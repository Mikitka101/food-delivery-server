package com.mikitayasiulevich.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import com.mikitayasiulevich.data.model.UserDBModel
import com.mikitayasiulevich.domain.repository.UserRepository
import io.ktor.server.auth.jwt.*
import java.util.*


class JwtService(
    private val userRepository: UserRepository,
) {

    private val issuer = "food-delivery-server"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val audience = System.getenv("JWT_AUDIENCE")
    val realm: String = System.getenv("JWT_REALM")
    private val algorithm = Algorithm.HMAC512(jwtSecret)


    val jwtVerifier: JWTVerifier =
        JWT
            .require(algorithm)
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    fun createAccessToken(login: String, role: String): String =
        createJwtToken(login, role, 86_400_000) // 86_400_000 - 24 hours

    fun createRefreshToken(login: String, role: String): String =
        createJwtToken(login, role, 7_776_000_000) // 90 days

    private fun createJwtToken(login: String, roles: String, expireIn: Long): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("login", login)
            .withClaim("roles", roles)
            .withExpiresAt(Date(System.currentTimeMillis() + expireIn))
            .sign(algorithm)


    suspend fun customValidator(
        credential: JWTCredential,
    ): JWTPrincipal? {
        val login: String? = extractLogin(credential)
        val foundUser: UserDBModel? = login?.let {
            userRepository.getUserByLogin(it)
        }

        return foundUser?.let {
            if (audienceMatches(credential))
                JWTPrincipal(credential.payload)
            else
                null
        }
    }

    private fun audienceMatches(
        credential: JWTCredential,
    ): Boolean =
        credential.payload.audience.contains(audience)

    fun audienceMatches(
        audience: String
    ): Boolean =
        this.audience == audience


    private fun extractLogin(credential: JWTCredential): String? =
        credential.payload.getClaim("login").asString()
}