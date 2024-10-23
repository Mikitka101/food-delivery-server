package com.mikitayasiulevich.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddRestaurantRequest(
    val id: Int? = null,
    val restaurantName: String,
    val restaurantDescription: String,
    val restaurantAddress: String,
    val restaurantCreateDate: String,
    val isVerified: Boolean = false
)
