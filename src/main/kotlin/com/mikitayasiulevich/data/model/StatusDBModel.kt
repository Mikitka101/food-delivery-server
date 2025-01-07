package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.Constants

enum class StatusModel(val id: Int)  {
    IS_NOT_ISSUED(0),
    POST(1),
    COOK(2),
    DELIVERY(3),
    COMPLETED(4)
}

fun String.getStatusByString(): StatusModel {
    return when(this) {
        Constants.Status.COOK -> StatusModel.COOK
        Constants.Status.DELIVERY -> StatusModel.DELIVERY
        Constants.Status.COMPLETED -> StatusModel.COMPLETED
        else -> StatusModel.POST
    }
}

fun Int.getStatusById(): StatusModel {
    return when(this) {
        StatusModel.COOK.id -> StatusModel.COOK
        StatusModel.DELIVERY.id -> StatusModel.DELIVERY
        StatusModel.COMPLETED.id -> StatusModel.COMPLETED
        else -> StatusModel.POST
    }
}

fun StatusModel.getStringByStatus() : String {
    return when(this) {
        StatusModel.COOK -> Constants.Status.COOK
        StatusModel.DELIVERY -> Constants.Status.DELIVERY
        StatusModel.COMPLETED -> Constants.Status.COMPLETED
        else -> Constants.Status.POST
    }
}

