package com.mikitayasiulevich.routes

import com.mikitayasiulevich.data.model.requests.CreateDishRequest
import com.mikitayasiulevich.data.model.requests.IdRequest
import com.mikitayasiulevich.data.model.responses.BaseResponse
import com.mikitayasiulevich.domain.model.DishList
import com.mikitayasiulevich.domain.usecase.DishUseCase
import com.mikitayasiulevich.domain.usecase.UserUseCase
import com.mikitayasiulevich.utils.Constants
import com.mikitayasiulevich.utils.Constants.Success.DISH_CREATED_SUCCESSFULLY
import com.mikitayasiulevich.utils.Constants.Success.DISH_DELETED_SUCCESSFULLY
import com.mikitayasiulevich.utils.Constants.Success.DISH_UPDATED_SUCCESSFULLY
import com.mikitayasiulevich.utils.authorized
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.dishRoute(
    dishUseCase: DishUseCase,
    userUseCase: UserUseCase
) {

    authenticate {
        authorized(
            Constants.Role.MODERATOR,
            Constants.Role.ADMIN,
            Constants.Role.CLIENT,
            Constants.Role.COURIER
        ) {
            post("/get-restaurant-dishes") {
                val restaurantIdRequest = call.receive<IdRequest>()

                val dishesListObj = DishList(dishUseCase.getRestaurantDishes(restaurantIdRequest.id))
                call.respond(HttpStatusCode.OK, dishesListObj)
            }
        }
    }
    authenticate {
        authorized(
            Constants.Role.ADMIN
        ) {
            post("/create-dish") {
                val createDishRequest = call.receive<CreateDishRequest>()

                val foundUser = userUseCase.findUserByLogin(extractPrincipalLogin(call) ?: "")
                    ?: return@post call.respond(HttpStatusCode.NotFound)

                dishUseCase.createDish(
                    createDishRequest, foundUser.id
                ) ?: return@post call.respond(HttpStatusCode.BadRequest)

                call.respond(HttpStatusCode.OK, BaseResponse(true, DISH_CREATED_SUCCESSFULLY))
            }

            post("/update-dish") {
                val createDishRequest = call.receive<CreateDishRequest>()

                val foundUser = userUseCase.findUserByLogin(extractPrincipalLogin(call) ?: "")
                    ?: return@post call.respond(HttpStatusCode.NotFound)

                dishUseCase.updateDish(
                    createDishRequest = createDishRequest,
                    adminId = foundUser.id,
                )

                call.respond(HttpStatusCode.OK, BaseResponse(true, DISH_UPDATED_SUCCESSFULLY))
            }

            delete("/delete-dish") {
                val deleteDishRequest = call.receive<IdRequest>()
                val id: UUID = UUID.fromString(deleteDishRequest.id)

                dishUseCase.deleteDish(id)

                call.respond(HttpStatusCode.OK, BaseResponse(true, DISH_DELETED_SUCCESSFULLY))
            }
        }
    }
}

private fun extractPrincipalLogin(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("login")
        ?.asString()