package com.mikitayasiulevich.data.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val token: String
)
