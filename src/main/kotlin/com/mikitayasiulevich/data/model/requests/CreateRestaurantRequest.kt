package com.mikitayasiulevich.data.model.requests

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateRestaurantRequest(
    val photo: String,
    val description: String,
    val category: String,
    val name: String,
    val address: String,
)
