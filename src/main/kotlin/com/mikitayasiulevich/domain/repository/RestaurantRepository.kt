package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.RestaurantDBModel
import com.mikitayasiulevich.data.model.requests.CreateRestaurantRequest
import java.util.*

interface RestaurantRepository {

    suspend fun addRestaurant(restaurantDBModel: RestaurantDBModel)

    suspend fun getAllRestaurants(): List<RestaurantDBModel>

    suspend fun getRestaurantById(restaurantId: UUID): RestaurantDBModel?

    suspend fun getRestaurantByName(restaurantName: String): RestaurantDBModel?

    suspend fun getRestaurantByAdmin(adminId: UUID): RestaurantDBModel?

    suspend fun updateRestaurant(restaurantDBModel: RestaurantDBModel, adminId: UUID)

    suspend fun deleteRestaurant(restaurantId: UUID)
}