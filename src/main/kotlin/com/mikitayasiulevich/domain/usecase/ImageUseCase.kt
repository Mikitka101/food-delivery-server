package com.mikitayasiulevich.domain.usecase

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


class ImageUseCase {

    suspend fun saveImage(fileBytes: ByteArray): String {
        val response: HttpResponse = HttpClient.client.submitFormWithBinaryData(
            url = "https://api.imgur.com/3/image/",
            formData = formData {
                append(
                    "image",
                    fileBytes,
                    Headers.build {
                        append("Authorization", "Client-ID b04f1f69f69e4e9")
                    }
                )
            }
        )
        val responseBody: String = response.bodyAsText()
        val imgurResponse: ImgurResponse = Json.decodeFromString(responseBody)
        
        return imgurResponse.data.link?: "null"
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
                json(Json{
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }
    }
}

@kotlinx.serialization.Serializable
data class ImgurResponse(
    val status: Int,
    val success: Boolean,
    val data: ImgurData
)

@Serializable
data class ImgurData(
    val account_id: String? = "",
    val account_url: String? = "",
    val ad_type: String? = "",
    val ad_url: String? = "",
    val animated: Boolean? = false,
    val bandwidth: Int? = 0,
    val datetime: Int? = 0,
    val deletehash: String? = "",
    val description: String? = "",
    val favorite: Boolean? = false,
    val has_sound: Boolean? = false,
    val height: Int? = 0,
    val hls: String? = "",
    val id: String? = "",
    val in_gallery: Boolean? = false,
    val in_most_viral: Boolean? = false,
    val is_ad: Boolean? = false,
    val link: String? = "",
    val mp4: String? = "",
    val name: String? = "",
    val nsfw: String? = "",
    val section: String? = "",
    val size: Int? = 0,
    val tags: List<String?> = listOf(),
    val title: String? = "",
    val type: String? = "",
    val views: Int? = 0,
    val vote: String? = "",
    val width: Int? = 0
)