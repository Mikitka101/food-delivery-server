package com.mikitayasiulevich.data.model.requests

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateDishRequest(
    val photo: String,
    val description: String,
    val name: String,
    val price: Float
)
