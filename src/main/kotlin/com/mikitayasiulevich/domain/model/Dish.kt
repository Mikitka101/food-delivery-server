package com.mikitayasiulevich.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Dish(
    val id: String,
    val restaurant: String,
    val photo: String,
    val description: String,
    val name: String,
    val price: Float,
    val active: Boolean,
    val banned: Boolean
)
