package com.mikitayasiulevich.data.model.responses

import com.mikitayasiulevich.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val login: String,
)
