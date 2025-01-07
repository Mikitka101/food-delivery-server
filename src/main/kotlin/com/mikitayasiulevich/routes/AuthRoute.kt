package com.mikitayasiulevich.routes

import com.mikitayasiulevich.data.model.requests.LoginRequest
import com.mikitayasiulevich.data.model.requests.RefreshTokenRequest
import com.mikitayasiulevich.data.model.responses.AuthResponse
import com.mikitayasiulevich.data.model.responses.RefreshTokenResponse
import com.mikitayasiulevich.domain.usecase.UserUseCase
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoute(userUseCase: UserUseCase) {

    post {
        val loginRequest = call.receive<LoginRequest>()

        val authResponse: AuthResponse? = userUseCase.authenticate(loginRequest)

        authResponse?.let {
            call.respond(authResponse)
        } ?: call.respond(
            message = HttpStatusCode.NotFound
        )
    }

    post("/refresh") {
        val request = call.receive<RefreshTokenRequest>()

        val newAccessToken = userUseCase.refreshToken(token = request.token)

        newAccessToken?.let {
            call.respond(
                RefreshTokenResponse(it)
            )
        } ?: call.respond(
            message = HttpStatusCode.Unauthorized
        )
    }
}