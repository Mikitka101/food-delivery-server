package com.mikitayasiulevich.domain.usecase

import arrow.core.Either
import com.mikitayasiulevich.data.model.responses.ImgurResponse
import com.mikitayasiulevich.domain.model.Photo
import com.mikitayasiulevich.domain.repository.PhotoRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.json.Json


class ImageUseCase(
    private val photoRepository: PhotoRepository
) {

    suspend fun saveImageRemote(fileBytes: ByteArray): Either<Exception, String> {
        return try {
            val response: String = HttpClient.client.submitFormWithBinaryData(
                url = "https://api.imgbb.com/1/upload?key=f641a975ec9ef72709484ed246a146e1",
                formData = formData {
                    append(
                        "image",
                        fileBytes.encodeBase64(),
                    )
                }
            ).bodyAsText()

            val json = Json { ignoreUnknownKeys = true}
            val url = json.decodeFromString<ImgurResponse>(response).data.url

            Either.Right(url)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(e)
        }
    }

    suspend fun saveImageDB(url: String): Either<Exception, Photo> {
        return try {
            val photoDBModel = photoRepository.addPhoto(url)
            val photo = Photo(
                photoDBModel.id,
                photoDBModel.url
            )
            Either.Right(photo)
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    object HttpClient {

        val client by lazy {
            HttpClient(CIO) {
                install(Logging) {
                    level = LogLevel.INFO
                    logger = Logger.ANDROID
                }
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    })
                }
            }
        }
    }
}

