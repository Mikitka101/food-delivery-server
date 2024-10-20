package com.mikitayasiulevich.domain.usecase

import com.mikitayasiulevich.data.model.RestaurantModel
import com.mikitayasiulevich.domain.repository.RestaurantRepository

class RestaurantUseCase(
    private val repository: RestaurantRepository,
) {

    suspend fun addRestaurant(restaurantModel: RestaurantModel) = repository.addRestaurant(restaurantModel)

    suspend fun getAllRestaurants() = repository.getAllRestaurants()

    suspend fun getRestaurantById(restaurantId: Int) = repository.getRestaurantById(restaurantId)

    suspend fun updateRestaurant(restaurantModel: RestaurantModel, restaurantAdminId: Int) =
        repository.updateRestaurant(restaurantModel, restaurantAdminId)

    suspend fun deleteRestaurant(restaurantId: Int, restaurantAdminId: Int) =
        repository.deleteRestaurant(restaurantId, restaurantAdminId)
}