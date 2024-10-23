package com.mikitayasiulevich.routes

import com.mikitayasiulevich.authentification.hash
import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.data.model.getRoleByString
import com.mikitayasiulevich.data.model.requests.LoginRequest
import com.mikitayasiulevich.data.model.requests.RegisterRequest
import com.mikitayasiulevich.data.model.responses.BaseResponse
import com.mikitayasiulevich.domain.usecase.UserUseCase
import com.mikitayasiulevich.utils.Constants.Error.GENERAL
import com.mikitayasiulevich.utils.Constants.Error.INCORRECT_PASSWORD
import com.mikitayasiulevich.utils.Constants.Error.USER_NOT_FOUND
import com.mikitayasiulevich.utils.Constants.Error.WRONG_EMAIL
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.UserRoute(userUseCase: UserUseCase) {

    val hashFunction = { p: String -> hash(password = p) }

    post("api/v1/signup") {
        call.receiveNullable<RegisterRequest>()?.let { registerRequest ->
            try {
                val user = UserModel(
                    id = 0,
                    email = registerRequest.email.trim().lowercase(),
                    login = registerRequest.login,
                    password = hashFunction(registerRequest.password.trim()),
                    firstName = registerRequest.firstName,
                    lastName = registerRequest.lastName,
                    isActive = false,
                    role = registerRequest.role.trim().getRoleByString()
                )

                userUseCase.createUser(user)
                call.respond(HttpStatusCode.OK, BaseResponse(true, userUseCase.generateToken(userModel = user)))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: GENERAL))
            }
        } ?: run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, GENERAL))
            return@post
        }
    }

    post("api/v1/login") {
        call.receiveNullable<LoginRequest>()?.let { loginRequest ->
            try {
                val user = userUseCase.findUserByEmail(loginRequest.email.trim().lowercase())

                if (user == null) {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, WRONG_EMAIL))
                } else {
                    if (user.password == hashFunction(loginRequest.password)) {
                        call.respond(HttpStatusCode.OK, BaseResponse(true, userUseCase.generateToken(userModel = user)))
                    } else {
                        call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, INCORRECT_PASSWORD))
                    }
                }

            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: GENERAL))
            }
        } ?: run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, GENERAL))
            return@post
        }
    }

    authenticate("jwt") {

        get("api/v1/get-user-info") {
            try {
                val user = call.principal<UserModel>()

                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, USER_NOT_FOUND))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, e.message ?: GENERAL))
            }
        }
    }
}
