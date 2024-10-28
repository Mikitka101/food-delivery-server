package com.mikitayasiulevich.routes

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

fun Route.imageRoute(imageUseCase: ImageUseCase) {

    authenticate {
        authorized(
            Constants.Role.MODERATOR,
            Constants.Role.ADMIN
        ) {
            post("/upload-image") {
                if (call.request.contentType().match(ContentType.MultiPart.FormData)) {

                    val multipartData = call.receiveMultipart()
                    var fileName: String? = null
                    var fileBytes: ByteArray? = null

                    multipartData.forEachPart { part ->
                        when (part) {
                            is PartData.FileItem -> {
                                fileName = part.originalFileName
                                fileBytes = part.provider().toByteArray()
                            }
                            else -> { }
                        }
                        part.dispose()
                    }

                    if (fileBytes != null && fileName != null) {

                        val url = imageUseCase.saveImage(fileBytes!!)

                        call.respond(HttpStatusCode.OK, "File uploaded successfully: $url")
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "No file uploaded")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Expecting multipart form data")
                }
            }
        }
    }
}