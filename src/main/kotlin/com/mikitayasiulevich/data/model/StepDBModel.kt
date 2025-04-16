package com.mikitayasiulevich.data.model

import com.mikitayasiulevich.utils.Constants
import com.mikitayasiulevich.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

enum class Step(val id: Int)  {
    CHOICE(1),
    CONFIRMATION(2),
    COOK(3),
    DELIVERY(4),
    CANCELLATION(5)
}

@Serializable
data class StepModel(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val order: UUID,
    val step: Step,
    val step_begin: String,
    val step_end: String
)

fun String.getStepByString(): Step {
    return when(this) {
        Constants.Step.CONFIRMATION -> Step.CONFIRMATION
        Constants.Step.COOK -> Step.COOK
        Constants.Step.DELIVERY -> Step.DELIVERY
        Constants.Step.CANCELLATION -> Step.CANCELLATION
        else -> Step.CHOICE
    }
}

fun Int.getStepById(): Step {
    return when(this) {
        Step.CONFIRMATION.id -> Step.CONFIRMATION
        Step.COOK.id -> Step.COOK
        Step.DELIVERY.id -> Step.DELIVERY
        Step.CANCELLATION.id -> Step.CANCELLATION
        else -> Step.CHOICE
    }
}

fun Step.getStringByStep() : String {
    return when(this) {
        Step.CONFIRMATION -> Constants.Step.CONFIRMATION
        Step.COOK -> Constants.Step.COOK
        Step.DELIVERY -> Constants.Step.DELIVERY
        Step.CANCELLATION -> Constants.Step.CANCELLATION
        else -> Constants.Step.CHOICE
    }
}

