package com.mikitayasiulevich.data.model

import java.util.*

data class UserModel(
    val id: UUID,
    val login: String,
    val password: String
)
