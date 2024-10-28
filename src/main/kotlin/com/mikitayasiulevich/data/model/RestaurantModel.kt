package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RestaurantModel(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val restaurantAdmin: UUID,
    val restaurantName: String,
    val restaurantDescription: String,
    val restaurantAddress: String,
    val restaurantCreateDate: String,
    val isVerified: Boolean
)
