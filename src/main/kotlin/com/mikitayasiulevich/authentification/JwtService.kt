package com.mikitayasiulevich.authentification

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import com.mikitayasiulevich.data.model.UserModel
import java.time.LocalDateTime
import java.time.ZoneOffset

class JwtService {

    private val issuer = "food-delivery-server"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    private val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: UserModel): String {
        return JWT.create()
            .withSubject("FoodDeliveryAppAuthentification")
            .withIssuer(issuer)
            .withClaim("email", user.email)
            .withExpiresAt(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))
            .sign(algorithm)
    }

    fun getVerifier(): JWTVerifier = verifier
}