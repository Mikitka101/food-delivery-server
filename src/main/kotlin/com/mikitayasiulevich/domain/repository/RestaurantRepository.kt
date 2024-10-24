package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.RestaurantModel
import java.util.*

interface RestaurantRepository {

    suspend fun addRestaurant(restaurantModel: RestaurantModel)

    suspend fun getAllRestaurants(): List<RestaurantModel>

    suspend fun getRestaurantById(restaurantId: Int): RestaurantModel?

    suspend fun updateRestaurant(restaurantModel: RestaurantModel, restaurantAdminId: UUID)

    suspend fun deleteRestaurant(restaurantId: Int, restaurantAdminId: UUID)
}