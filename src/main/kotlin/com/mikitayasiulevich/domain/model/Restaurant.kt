package com.mikitayasiulevich.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: String,
    val admin: String,
    val photo: String,
    val description: String,
    val address: String,
    val name: String,
    val active: Boolean,
    val banned: Boolean
)
