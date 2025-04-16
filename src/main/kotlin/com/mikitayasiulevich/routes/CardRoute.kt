package com.mikitayasiulevich.routes

import arrow.core.Either
import com.mikitayasiulevich.domain.usecase.ImageUseCase
import com.mikitayasiulevich.utils.Constants
import com.mikitayasiulevich.utils.authorized
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.cardRoute() {

    authenticate {
        authorized(
            Constants.Role.MODERATOR,
            Constants.Role.ADMIN,
            Constants.Role.CLIENT,
            Constants.Role.COURIER,
        ) {
            post("/create-card") {

            }

            delete("/delete-card") {

            }
        }
    }
}