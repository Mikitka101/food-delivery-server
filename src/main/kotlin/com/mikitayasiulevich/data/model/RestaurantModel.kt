package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class RestaurantModel(
    val id: Int,
    @Serializable(with = UUIDSerializer::class)
    val restaurantAdmin: UUID,
    val restaurantName: String,
    val restaurantDescription: String,
    val restaurantAddress: String,
    val restaurantCreateDate: String,
    val isVerified: Boolean = false
)
