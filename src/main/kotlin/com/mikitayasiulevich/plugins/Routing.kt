package com.mikitayasiulevich.plugins

import com.mikitayasiulevich.domain.usecase.RestaurantUseCase
import com.mikitayasiulevich.domain.usecase.UserUseCase
import com.mikitayasiulevich.routes.UserRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userUseCase: UserUseCase, restaurantUseCase: RestaurantUseCase) {
    routing {
        UserRoute(userUseCase = userUseCase)
    }
}
