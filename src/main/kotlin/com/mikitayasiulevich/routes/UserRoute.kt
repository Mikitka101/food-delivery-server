package com.mikitayasiulevich.routes

import com.mikitayasiulevich.data.model.UserDBModel
import com.mikitayasiulevich.data.model.getStringByRole
import com.mikitayasiulevich.data.model.requests.IdRequest
import com.mikitayasiulevich.data.model.requests.RegisterRequest
import com.mikitayasiulevich.data.model.responses.UserResponse
import com.mikitayasiulevich.domain.usecase.UserUseCase
import com.mikitayasiulevich.utils.Constants
import com.mikitayasiulevich.utils.authorized
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
            registerRequest = registerRequest
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
        authorized(Constants.Role.MODERATOR) {
            get {
                val users = userUseCase.findAllUsers()

                call.respond(
                    message = users.map(UserDBModel::toResponse)
                )
            }
        }
    }

    authenticate {
        authorized(Constants.Role.MODERATOR) {
            get("/get-user-info") {
                val getUserByIdRequest = call.receive<IdRequest>()
                val id: String = getUserByIdRequest.id

                val foundUser = userUseCase.findUserById(id)
                    ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(
                    message = foundUser.toResponse()
                )
            }
        }
    }

    authenticate {
        authorized(
            Constants.Role.MODERATOR,
            Constants.Role.ADMIN,
            Constants.Role.CLIENT,
            Constants.Role.COURIER
        ) {
            get("/get-personal-data") {

                val foundUser = userUseCase.findUserByLogin(extractPrincipalLogin(call) ?: "")
                    ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(
                    message = foundUser.toResponse()
                )
            }
        }
    }
}

private fun UserDBModel.toResponse(): UserResponse =
    UserResponse(
        id = this.id,
        login = this.login,
        name = this.name,
        roles = this.roles.map { it.getStringByRole() }
    )

private fun extractPrincipalLogin(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("login")
        ?.asString()

