package com.mikitayasiulevich.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val token: String
)
