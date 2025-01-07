package com.mikitayasiulevich.plugins

import com.mikitayasiulevich.domain.usecase.DishUseCase
import com.mikitayasiulevich.domain.usecase.ImageUseCase
import com.mikitayasiulevich.domain.usecase.RestaurantUseCase
import com.mikitayasiulevich.domain.usecase.UserUseCase
import com.mikitayasiulevich.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userUseCase: UserUseCase,
    restaurantUseCase: RestaurantUseCase,
    dishUseCase: DishUseCase,
    imageUseCase: ImageUseCase
) {

    routing {

        route("/api/v1/auth") {
            authRoute(userUseCase = userUseCase)
        }

        route("/api/v1/users") {
            userRoute(userUseCase = userUseCase)
        }

        route("/api/v1/restaurants") {
            restaurantRoute(restaurantUseCase = restaurantUseCase, userUseCase = userUseCase)
        }

        route("/api/v1/images") {
            imageRoute(imageUseCase = imageUseCase)
        }

        route("/api/v1/dishes") {
            dishRoute(dishUseCase = dishUseCase, userUseCase = userUseCase)
        }
    }
}
