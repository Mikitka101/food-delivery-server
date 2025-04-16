package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RestaurantDBModel(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val admin: UUID,
    @Serializable(with = UUIDSerializer::class)
    val photo: UUID,
    @Serializable(with = UUIDSerializer::class)
    val description: UUID,
    @Serializable(with = UUIDSerializer::class)
    val address: UUID,
    val name: String,
    val active: Boolean,
    val banned: Boolean
)
