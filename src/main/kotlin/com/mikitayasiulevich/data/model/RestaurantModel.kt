package com.mikitayasiulevich.data.model

data class RestaurantModel(
    val id: Int,
    val restaurantAdmin: Int,
    val restaurantName: String,
    val restaurantDescription: String,
    val restaurantAddress: String,
    val restaurantCreateDate: String,
    val isVerified: Boolean = false
)
