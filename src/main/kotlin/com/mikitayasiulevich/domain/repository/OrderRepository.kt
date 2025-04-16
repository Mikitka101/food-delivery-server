package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.OrderDBModel
import com.mikitayasiulevich.data.model.RestaurantDBModel
import com.mikitayasiulevich.data.model.requests.CreateRestaurantRequest
import java.util.*

interface OrderRepository {

    suspend fun addOrder(orderDBModel: OrderDBModel)

    suspend fun getRestaurantOrders(): List<OrderDBModel>

    suspend fun getOrderById(ordertId: UUID): OrderDBModel?

    suspend fun getOrdersByUser(userId: UUID): List<OrderDBModel>

    suspend fun updateOrder(ordertDBModel: OrderDBModel)

    suspend fun deleteOrder(orderId: UUID)
}