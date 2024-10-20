package com.mikitayasiulevich.plugins

import com.mikitayasiulevich.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(userUseCase: UserUseCase) {

    authentication {
        jwt("jwt") {
            verifier(userUseCase.getJwtVerifier())
            realm = "Food Delivery Service Server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.findUserByEmail(email)
                user
            }
        }
    }
}
