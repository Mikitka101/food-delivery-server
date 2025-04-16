package com.mikitayasiulevich.data.model

import java.util.*

data class UserDBModel(
    val id: UUID,
    val login: String,
    val password: String,
    val name: String,
    val banned: Boolean,
    val roles: List<Role>,
    val addresses: List<AddressDBModel>,
    val cards: List<CardDBModel>
)
