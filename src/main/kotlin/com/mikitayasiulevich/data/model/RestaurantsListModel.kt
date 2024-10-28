package com.mikitayasiulevich.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantsListModel(
    val restaurants: List<RestaurantModel>
)