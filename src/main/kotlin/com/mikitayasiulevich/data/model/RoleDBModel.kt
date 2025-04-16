package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.Constants

enum class Role(val id: Int)  {
    MODERATOR(1),
    ADMIN(2),
    CLIENT(3),
    COURIER(4)
}

fun String.getRoleByString(): Role {
    return when(this) {
        Constants.Role.ADMIN -> Role.ADMIN
        Constants.Role.MODERATOR -> Role.MODERATOR
        Constants.Role.COURIER -> Role.COURIER
        else -> Role.CLIENT
    }
}

fun Int.getRoleById(): Role {
    return when(this) {
        Role.ADMIN.id -> Role.ADMIN
        Role.MODERATOR.id -> Role.MODERATOR
        Role.COURIER.id -> Role.COURIER
        else -> Role.CLIENT
    }
}

fun Role.getStringByRole() : String {
    return when(this) {
        Role.ADMIN -> Constants.Role.ADMIN
        Role.MODERATOR -> Constants.Role.MODERATOR
        Role.COURIER -> Constants.Role.COURIER
        else -> Constants.Role.CLIENT
    }
}

