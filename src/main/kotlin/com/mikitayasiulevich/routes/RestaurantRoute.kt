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

fun Route.restaurantRoute(
    restaurantUseCase: RestaurantUseCase,
    userUseCase: UserUseCase
) {

    authenticate {
        authorized(
            Constants.Role.MODERATOR,
            Constants.Role.ADMIN,
            Constants.Role.CLIENT,
            Constants.Role.COURIER
        ) {
            get {
                val restaurantsListObj = RestaurantList(restaurantUseCase.getAllRestaurants())
                call.respond(HttpStatusCode.OK, restaurantsListObj)
            }
            post("/restaurant-info") {
                val restaurantRequest = call.receive<IdRequest>()

                val foundRestaurant =
                    restaurantUseCase.getRestaurantById(restaurantId = UUID.fromString(restaurantRequest.id))
                        ?: return@post call.respond(HttpStatusCode.NotFound)

                call.respond(HttpStatusCode.OK, foundRestaurant)
            }
        }
    }
    authenticate {
        authorized(
            Constants.Role.ADMIN
        ) {
            post("/create-restaurant") {
                val createRestaurantRequest = call.receive<CreateRestaurantRequest>()

                val foundUser = userUseCase.findUserByLogin(extractPrincipalLogin(call) ?: "")
                    ?: return@post call.respond(HttpStatusCode.NotFound)

                restaurantUseCase.createRestaurant(
                    createRestaurantRequest, foundUser.id
                ) ?: return@post call.respond(HttpStatusCode.BadRequest)

                call.respond(HttpStatusCode.OK, BaseResponse(true, RESTAURANT_CREATED_SUCCESSFULLY))
            }

            post("/update-restaurant") {
                val createRestaurantRequest = call.receive<CreateRestaurantRequest>()

                val foundUser = userUseCase.findUserByLogin(extractPrincipalLogin(call) ?: "")
                    ?: return@post call.respond(HttpStatusCode.NotFound)

                restaurantUseCase.updateRestaurant(
                    createRestaurantRequest = createRestaurantRequest,
                    adminId = foundUser.id,
                )

                call.respond(HttpStatusCode.OK, BaseResponse(true, RESTAURANT_UPDATED_SUCCESSFULLY))
            }

            delete("/delete-restaurant") {
                val deleteRestaurantRequest = call.receive<IdRequest>()
                val id: UUID = UUID.fromString(deleteRestaurantRequest.id)

                restaurantUseCase.deleteRestaurant(id)

                call.respond(HttpStatusCode.OK, BaseResponse(true, RESTAURANT_DELETED_SUCCESSFULLY))
            }
        }
    }
}

private fun extractPrincipalLogin(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("login")
        ?.asString()