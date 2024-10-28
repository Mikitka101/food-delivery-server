package com.mikitayasiulevich.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class IdAndAdminIdRequest(
    val id: String,
    val adminId: String
)
