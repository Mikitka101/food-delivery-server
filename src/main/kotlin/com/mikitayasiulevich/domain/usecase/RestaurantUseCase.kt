package com.mikitayasiulevich.domain.usecase

import com.mikitayasiulevich.data.model.RestaurantModel
import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.domain.repository.RestaurantRepository
import java.util.UUID

class RestaurantUseCase(
    private val repository: RestaurantRepository,
) {
    suspend fun createRestaurant(restaurantModel: RestaurantModel): RestaurantModel? {
        val foundRestaurant = repository.getRestaurantByName(restaurantModel.restaurantName)
        return if (foundRestaurant == null) {
            repository.addRestaurant(restaurantModel)
            restaurantModel
        } else null
    }

    suspend fun getAllRestaurants() = repository.getAllRestaurants()

    suspend fun getRestaurantById(restaurantId: UUID) = repository.getRestaurantById(restaurantId)

    suspend fun updateRestaurant(restaurantModel: RestaurantModel, restaurantAdminId: UUID) =
        repository.updateRestaurant(restaurantModel, restaurantAdminId)

    suspend fun deleteRestaurant(restaurantId: UUID, restaurantAdminId: UUID) =
        repository.deleteRestaurant(restaurantId, restaurantAdminId)
}