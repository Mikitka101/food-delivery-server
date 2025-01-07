package com.mikitayasiulevich.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DishList(
    val dishes: List<Dish>
)
