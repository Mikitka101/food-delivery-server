package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.RestaurantModel

interface RestaurantRepository {

    suspend fun addRestaurant(restaurantModel: RestaurantModel)

    suspend fun getAllRestaurants(): List<RestaurantModel>

    suspend fun getRestaurantById(restaurantId: Int): RestaurantModel?

    suspend fun updateRestaurant(restaurantModel: RestaurantModel, restaurantAdminId: Int)

    suspend fun deleteRestaurant(restaurantId: Int, restaurantAdminId: Int)
}