package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.Constants

enum class RoleModel {
    MODERATOR,      //delivery service moderator
    ADMIN,          //restaurant admin
    CLIENT,
    COURIER
}

fun String.getRoleByString(): RoleModel {
    return when (this) {
        Constants.Role.MODERATOR -> RoleModel.MODERATOR
        Constants.Role.ADMIN -> RoleModel.ADMIN
        Constants.Role.COURIER -> RoleModel.COURIER
        else -> RoleModel.CLIENT
    }
}

fun RoleModel.getStringByRole(): String {
    return when (this) {
        RoleModel.MODERATOR -> Constants.Role.MODERATOR
        RoleModel.ADMIN -> Constants.Role.ADMIN
        RoleModel.COURIER -> Constants.Role.COURIER
        else -> Constants.Role.CLIENT
    }
}