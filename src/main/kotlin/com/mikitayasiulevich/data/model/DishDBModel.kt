package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DishDBModel(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val restaurant: UUID,
    @Serializable(with = UUIDSerializer::class)
    val photo: UUID,
    @Serializable(with = UUIDSerializer::class)
    val description: UUID,
    val name: String,
    val price: Float,
    val active: Boolean,
    val banned: Boolean
)
