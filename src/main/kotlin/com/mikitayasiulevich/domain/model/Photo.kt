package com.mikitayasiulevich.domain.model

import com.mikitayasiulevich.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Photo (
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val url: String
)