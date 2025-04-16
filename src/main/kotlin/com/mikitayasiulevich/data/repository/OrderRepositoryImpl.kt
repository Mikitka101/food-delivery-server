package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.model.OrderDBModel
import com.mikitayasiulevich.domain.repository.OrderRepository
import java.util.*

class OrderRepositoryImpl: OrderRepository {
    override suspend fun addOrder(orderDBModel: OrderDBModel) {
        return
    }

    override suspend fun getRestaurantOrders(): List<OrderDBModel> {
        return emptyList()
    }

    override suspend fun getOrderById(ordertId: UUID): OrderDBModel? {
        return null
    }

    override suspend fun getOrdersByUser(userId: UUID): List<OrderDBModel> {
        return emptyList()
    }

    override suspend fun updateOrder(ordertDBModel: OrderDBModel) {
        return
    }

    override suspend fun deleteOrder(orderId: UUID) {
        return
    }
}