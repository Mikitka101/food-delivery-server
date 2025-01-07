package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.Constants

enum class RoleModel(val id: Int)  {
    MODERATOR(1),
    ADMIN(2),
    CLIENT(3),
    COURIER(4)
}

fun String.getRoleByString(): RoleModel {
    return when(this) {
        Constants.Role.ADMIN -> RoleModel.ADMIN
        Constants.Role.MODERATOR -> RoleModel.MODERATOR
        Constants.Role.COURIER -> RoleModel.COURIER
        else -> RoleModel.CLIENT
    }
}

fun Int.getRoleById(): RoleModel {
    return when(this) {
        RoleModel.ADMIN.id -> RoleModel.ADMIN
        RoleModel.MODERATOR.id -> RoleModel.MODERATOR
        RoleModel.COURIER.id -> RoleModel.COURIER
        else -> RoleModel.CLIENT
    }
}

fun RoleModel.getStringByRole() : String {
    return when(this) {
        RoleModel.ADMIN -> Constants.Role.ADMIN
        RoleModel.MODERATOR -> Constants.Role.MODERATOR
        RoleModel.COURIER -> Constants.Role.COURIER
        else -> Constants.Role.CLIENT
    }
}

