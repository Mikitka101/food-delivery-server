package com.mikitayasiulevich.routes

import com.mikitayasiulevich.data.model.requests.CreateRestaurantRequest
import com.mikitayasiulevich.data.model.requests.IdRequest
import com.mikitayasiulevich.data.model.responses.BaseResponse
import com.mikitayasiulevich.domain.model.RestaurantList
import com.mikitayasiulevich.domain.usecase.RestaurantUseCase
import com.mikitayasiulevich.domain.usecase.UserUseCase
import com.mikitayasiulevich.utils.Constants
import com.mikitayasiulevich.utils.Constants.Success.RESTAURANT_CREATED_SUCCESSFULLY
import com.mikitayasiulevich.utils.Constants.Success.RESTAURANT_DELETED_SUCCESSFULLY
import com.mikitayasiulevich.utils.Constants.Success.RESTAURANT_UPDATED_SUCCESSFULLY
import com.mikitayasiulevich.utils.authorized
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.orderRoute() {

    authenticate {
        authorized(
            Constants.Role.MODERATOR,
            Constants.Role.ADMIN,
            Constants.Role.CLIENT,
            Constants.Role.COURIER
        ) {
            get {
                call.respond(HttpStatusCode.OK, "")
            }
            post("/restaurant-info") {
                call.respond(HttpStatusCode.OK, "")
            }
        }
    }
    authenticate {
        authorized(
            Constants.Role.ADMIN
        ) {
            post("/create-restaurant") {
                call.respond(HttpStatusCode.OK, "")
            }

            post("/update-restaurant") {
                call.respond(HttpStatusCode.OK, "")
            }

            delete("/delete-restaurant") {
                call.respond(HttpStatusCode.OK, "")
            }
        }
    }
}

private fun extractPrincipalLogin(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("login")
        ?.asString()