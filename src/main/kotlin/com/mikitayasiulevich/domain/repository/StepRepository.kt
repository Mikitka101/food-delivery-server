package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.Role
import com.mikitayasiulevich.data.model.Step
import com.mikitayasiulevich.data.model.StepModel
import java.util.*

interface StepRepository {

    suspend fun addStepToOrder(order: UUID, step: StepModel)

    suspend fun getOrderSteps(orderId: UUID): List<StepModel>

}