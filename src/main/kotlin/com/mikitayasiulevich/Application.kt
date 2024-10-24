package com.mikitayasiulevich

import com.mikitayasiulevich.auth.JwtService
import com.mikitayasiulevich.data.repository.RefreshTokenRepositoryImpl
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
    val userRepository = UserRepositoryImpl()
    val jwtService = JwtService(userRepository)
    val refreshTokenRepositoryImpl = RefreshTokenRepositoryImpl()
    val restaurantRepository = RestaurantRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, refreshTokenRepositoryImpl, jwtService)
    val restaurantUseCase = RestaurantUseCase(restaurantRepository)


    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(jwtService)
    configureRouting(userUseCase, restaurantUseCase)
}
