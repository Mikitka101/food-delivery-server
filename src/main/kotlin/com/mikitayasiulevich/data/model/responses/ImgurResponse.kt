package com.mikitayasiulevich.data.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class ImgurResponse(
    val status: Int,
    val success: Boolean,
    val data: ImgurData
)

@Serializable
data class ImgurData(
    val url: String
)