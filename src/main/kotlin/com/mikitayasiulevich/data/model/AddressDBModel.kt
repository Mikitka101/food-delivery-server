package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AddressDBModel(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val address: String,
)
