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

fun Route.imageRoute(imageUseCase: ImageUseCase) {

    authenticate {
        authorized(
            Constants.Role.MODERATOR,
            Constants.Role.ADMIN
        ) {
            post("/upload-image") {

                if (!call.request.contentType().match(ContentType.MultiPart.FormData))
                    call.respond(HttpStatusCode.BadRequest, "Expecting multipart form data")

                val multipartData = call.receiveMultipart()
                var fileName: String? = null
                var fileBytes: ByteArray? = null

                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FileItem -> {
                            fileName = part.originalFileName
                            fileBytes = part.provider().toByteArray()
                        }
                        else -> {}
                    }
                    part.dispose()
                }

                if (fileBytes != null && fileName != null) {

                    val resultRemote = imageUseCase.saveImageRemote(fileBytes!!)

                    when(resultRemote){
                        is Either.Left -> {
                            call.respond(HttpStatusCode.BadRequest, "Error while saving on ImgBB")
                        }
                        is Either.Right -> {
                            val resultLocal = imageUseCase.saveImageDB(resultRemote.value)
                            if(resultLocal is Either.Right)
                                call.respond(HttpStatusCode.Created, Json.encodeToString(resultLocal.value))
                            call.respond(HttpStatusCode.BadRequest, "Error while saving on PostgreSQL")
                        }
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "No file uploaded")
                }
            }
        }
    }
}