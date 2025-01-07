package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.domain.repository.OrderRepository
import java.util.*

class OrderRepositoryImpl: OrderRepository {
    override suspend fun addOrder(orderDBModel: OrderDBModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getRestaurantOrders(): List<OrderDBModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrderById(ordertId: UUID): OrderDBModel? {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByUser(userId: UUID): List<OrderDBModel> {
        TODO("Not yet implemented")
    }

    override suspend fun updateOrder(ordertDBModel: OrderDBModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOrder(orderId: UUID) {
        TODO("Not yet implemented")
    }
}