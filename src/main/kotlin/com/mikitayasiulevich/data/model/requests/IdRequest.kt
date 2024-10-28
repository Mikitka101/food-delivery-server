package com.mikitayasiulevich.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class IdRequest(
    val id: String
)