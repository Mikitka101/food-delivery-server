package com.mikitayasiulevich.routes

import com.mikitayasiulevich.data.model.RestaurantModel
import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.data.model.requests.AddRestaurantRequest
import com.mikitayasiulevich.data.model.responses.BaseResponse
import com.mikitayasiulevich.domain.usecase.RestaurantUseCase
import com.mikitayasiulevich.utils.Constants
import com.mikitayasiulevich.utils.Constants.Error.GENERAL
import com.mikitayasiulevich.utils.Constants.Error.MISSING_FIELDS
import com.mikitayasiulevich.utils.Constants.Success.RESTAURANT_ADDED_SUCCESSFULLY
import com.mikitayasiulevich.utils.Constants.Success.RESTAURANT_DELETED_SUCCESSFULLY
import com.mikitayasiulevich.utils.Constants.Success.RESTAURANT_UPDATED_SUCCESSFULLY
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.RestaurantRoute(restaurantUseCase: RestaurantUseCase) {

    authenticate("jwt") {

        get("api/v1/get-all-restaurants") {
            try {
                val restaurants = restaurantUseCase.getAllRestaurants()
                call.respond(HttpStatusCode.OK, restaurants)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: GENERAL))
            }
        }

        post("api/v1/create-restaurant") {
            call.receiveNullable<AddRestaurantRequest>()?.let { restaurantRequest ->
                try {
                    val restaurant = RestaurantModel(
                        id = 0,
                        restaurantAdmin = call.principal<UserModel>()!!.id,
                        restaurantName = restaurantRequest.restaurantName,
                        restaurantDescription = restaurantRequest.restaurantDescription,
                        restaurantAddress = restaurantRequest.restaurantAddress,
                        restaurantCreateDate = restaurantRequest.restaurantCreateDate,
                        isVerified = restaurantRequest.isVerified
                    )

                    restaurantUseCase.addRestaurant(restaurant)
                    call.respond(HttpStatusCode.OK, BaseResponse(true, RESTAURANT_ADDED_SUCCESSFULLY))

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: GENERAL))
                }
            } ?: run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, MISSING_FIELDS))
                return@post
            }
        }

        post("api/v1/update-restaurant") {
            call.receiveNullable<AddRestaurantRequest>()?.let { restaurantRequest ->
                try {
                    val adminId = call.principal<UserModel>()!!.id
                    val restaurant = RestaurantModel(
                        id = restaurantRequest.id ?: 0,
                        restaurantAdmin = adminId,
                        restaurantName = restaurantRequest.restaurantName,
                        restaurantDescription = restaurantRequest.restaurantDescription,
                        restaurantAddress = restaurantRequest.restaurantAddress,
                        restaurantCreateDate = restaurantRequest.restaurantCreateDate,
                        isVerified = restaurantRequest.isVerified
                    )

                    restaurantUseCase.updateRestaurant(restaurant, adminId)
                    call.respond(HttpStatusCode.OK, BaseResponse(true, RESTAURANT_UPDATED_SUCCESSFULLY))

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: GENERAL))
                }
            } ?: run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, MISSING_FIELDS))
                return@post
            }
        }

        delete("api/v1/delete-restaurant") {
            call.request.queryParameters[Constants.Value.ID]?.toInt()?.let { id ->
                try {
                    val adminId = call.principal<UserModel>()!!.id

                    restaurantUseCase.deleteRestaurant(
                        restaurantId = id,
                        restaurantAdminId = adminId
                    )
                    call.respond(HttpStatusCode.OK, BaseResponse(true, RESTAURANT_DELETED_SUCCESSFULLY))

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: GENERAL))
                }
            } ?: run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, MISSING_FIELDS))
                return@delete
            }
        }
    }
}