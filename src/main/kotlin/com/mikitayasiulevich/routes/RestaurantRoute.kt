package com.mikitayasiulevich.routes

import com.mikitayasiulevich.data.model.RestaurantModel
import com.mikitayasiulevich.data.model.requests.CreateRestaurantRequest
import com.mikitayasiulevich.data.model.requests.IdAndAdminIdRequest
import com.mikitayasiulevich.data.model.requests.UpdateRestaurantRequest
import com.mikitayasiulevich.data.model.responses.BaseResponse
import com.mikitayasiulevich.domain.usecase.RestaurantUseCase
import com.mikitayasiulevich.utils.Constants
import com.mikitayasiulevich.utils.Constants.Success.RESTAURANT_CREATED_SUCCESSFULLY
import com.mikitayasiulevich.utils.Constants.Success.RESTAURANT_DELETED_SUCCESSFULLY
import com.mikitayasiulevich.utils.Constants.Success.RESTAURANT_UPDATED_SUCCESSFULLY
import com.mikitayasiulevich.utils.authorized
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun Route.restaurantRoute(restaurantUseCase: RestaurantUseCase) {

    authenticate {
        authorized(
            Constants.Role.MODERATOR,
            Constants.Role.ADMIN,
            Constants.Role.CLIENT,
            Constants.Role.COURIER
        ) {
            get {
                val restaurantsList = restaurantUseCase.getAllRestaurants()
                //println(Json.encodeToString(restaurantsList))
                call.respond(HttpStatusCode.OK, restaurantsList)
            }
        }
    }
    authenticate {
        authorized(
            Constants.Role.ADMIN
        ) {
            post("/create-restaurant") {
                val createRestaurantRequest = call.receive<CreateRestaurantRequest>()

                restaurantUseCase.createRestaurant(
                    restaurantModel = createRestaurantRequest.toModel()
                ) ?: return@post call.respond(HttpStatusCode.BadRequest)

                call.respond(HttpStatusCode.OK, BaseResponse(true, RESTAURANT_CREATED_SUCCESSFULLY))
            }

            post("/update-restaurant") {
                val updateRestaurantRequest = call.receive<UpdateRestaurantRequest>()

                restaurantUseCase.updateRestaurant(
                    restaurantModel = updateRestaurantRequest.toModel(),
                    restaurantAdminId = UUID.fromString(updateRestaurantRequest.restaurantAdmin)
                )

                call.respond(HttpStatusCode.OK, BaseResponse(true, RESTAURANT_UPDATED_SUCCESSFULLY))
            }

            delete("/delete-restaurant") {
                val deleteRestaurantRequest = call.receive<IdAndAdminIdRequest>()
                val id: UUID = UUID.fromString(deleteRestaurantRequest.id)
                val adminId: UUID = UUID.fromString(deleteRestaurantRequest.adminId)

                restaurantUseCase.deleteRestaurant(id, adminId)

                call.respond(HttpStatusCode.OK, BaseResponse(true, RESTAURANT_DELETED_SUCCESSFULLY))
            }
        }
    }
}

private fun CreateRestaurantRequest.toModel(): RestaurantModel =
    RestaurantModel(
        id = UUID.randomUUID(),
        restaurantAdmin = UUID.fromString(this.restaurantAdmin),
        restaurantName = this.restaurantName,
        restaurantAddress = this.restaurantAddress,
        restaurantDescription = this.restaurantDescription,
        restaurantCreateDate = this.restaurantCreateDate,
        isVerified = false
    )

private fun UpdateRestaurantRequest.toModel(): RestaurantModel =
    RestaurantModel(
        id = UUID.fromString(this.id),
        restaurantAdmin = UUID.fromString(this.restaurantAdmin),
        restaurantName = this.restaurantName,
        restaurantAddress = this.restaurantAddress,
        restaurantDescription = this.restaurantDescription,
        restaurantCreateDate = this.restaurantCreateDate,
        isVerified = false
    )