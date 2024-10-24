package com.mikitayasiulevich.routes

import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.data.model.requests.RegisterRequest
import com.mikitayasiulevich.data.model.responses.UserResponse
import com.mikitayasiulevich.domain.usecase.UserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoute(userUseCase: UserUseCase) {

    post("/register") {
        val registerRequest = call.receive<RegisterRequest>()

        val createdUser = userUseCase.createUser(
            userModel = registerRequest.toModel()
        ) ?: return@post call.respond(HttpStatusCode.BadRequest)

        call.response.header(
            name = "id",
            value = createdUser.id.toString()
        )
        call.respond(
            message = HttpStatusCode.Created
        )
    }

    authenticate {
        get {
            val users = userUseCase.findAllUsers()

            call.respond(
                message = users.map(UserModel::toResponse)
            )
        }
    }

    authenticate("another-auth") {
        get("/{id}") {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val foundUser = userUseCase.findUserById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)

            if (foundUser.login != extractPrincipalLogin(call))
                return@get call.respond(HttpStatusCode.NotFound)

            call.respond(
                message = foundUser.toResponse()
            )
        }
    }
}

private fun RegisterRequest.toModel(): UserModel =
    UserModel(
        id = UUID.randomUUID(),
        login = this.login,
        password = this.password
    )

private fun UserModel.toResponse(): UserResponse =
    UserResponse(
        id = this.id,
        login = this.login,
    )

private fun extractPrincipalLogin(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("login")
        ?.asString()

