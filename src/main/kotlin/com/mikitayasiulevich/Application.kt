package com.mikitayasiulevich

import com.mikitayasiulevich.authentification.JwtService
import com.mikitayasiulevich.data.repository.RestaurantRepositoryImpl
import com.mikitayasiulevich.data.repository.UserRepositoryImpl
import com.mikitayasiulevich.domain.usecase.RestaurantUseCase
import com.mikitayasiulevich.domain.usecase.UserUseCase
import com.mikitayasiulevich.plugins.*
import com.mikitayasiulevich.plugins.DatabaseFactory.initializationDatabase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val restaurantRepository = RestaurantRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jwtService)
    val restaurantUseCase = RestaurantUseCase(restaurantRepository)


    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)

    /*
    configureRouting()
    */
}
