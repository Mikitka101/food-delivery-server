package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class OrderDBModel(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val user: UUID,
    @Serializable(with = UUIDSerializer::class)
    val restaurant: UUID,
    @Serializable(with = UUIDSerializer::class)
    val status: String,
    val posted: String,
    val received: String,
)
