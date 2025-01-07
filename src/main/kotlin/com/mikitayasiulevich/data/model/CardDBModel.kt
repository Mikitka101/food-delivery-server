package com.mikitayasiulevich.data.model

import java.util.UUID

data class CardDBModel(
    val id: UUID,
    val number: String,
    val date: String
)
