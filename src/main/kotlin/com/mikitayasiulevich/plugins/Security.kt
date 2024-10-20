package com.mikitayasiulevich.plugins

import com.mikitayasiulevich.authentification.JwtService
import com.mikitayasiulevich.data.model.RoleModel
import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.data.repository.UserRepositoryImpl
import com.mikitayasiulevich.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import kotlinx.coroutines.runBlocking

fun Application.configureSecurity() {
    val jwtService = JwtService()
    val repository = UserRepositoryImpl()
    val userUseCase = UserUseCase(repository, jwtService)

    runBlocking {
        userUseCase.createUser(
            UserModel(
                id = 1,
                email = "moderator@gmail.com",
                login = "moderator",
                password = "moderator",
                firstName = "Mikita",
                lastName = "Yasiulevich",
                isActive = true,
                role = RoleModel.MODERATOR
            )
        )
    }

    authentication {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
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
