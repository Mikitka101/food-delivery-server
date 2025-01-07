package com.mikitayasiulevich.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantList(
    val restaurants: List<Restaurant>
)
