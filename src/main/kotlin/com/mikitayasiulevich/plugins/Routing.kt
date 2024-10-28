package com.mikitayasiulevich.plugins

import com.mikitayasiulevich.domain.usecase.ImageUseCase
import com.mikitayasiulevich.domain.usecase.RestaurantUseCase
import com.mikitayasiulevich.domain.usecase.UserUseCase
import com.mikitayasiulevich.routes.authRoute
import com.mikitayasiulevich.routes.imageRoute
import com.mikitayasiulevich.routes.restaurantRoute
import com.mikitayasiulevich.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userUseCase: UserUseCase,
    restaurantUseCase: RestaurantUseCase,
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
            restaurantRoute(restaurantUseCase = restaurantUseCase)
        }

        route("/api/v1/images") {
            imageRoute(imageUseCase = imageUseCase)
        }
    }

}
