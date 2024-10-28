package com.mikitayasiulevich.data.model.requests

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdateRestaurantRequest(
    val id: String,
    val restaurantAdmin: String,
    val restaurantName: String,
    val restaurantDescription: String,
    val restaurantAddress: String,
    val restaurantCreateDate: String,
)
