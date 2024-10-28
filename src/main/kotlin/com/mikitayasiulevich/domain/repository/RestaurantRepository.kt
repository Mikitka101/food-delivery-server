package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.RestaurantModel
import com.mikitayasiulevich.data.model.RestaurantsListModel
import java.util.*

interface RestaurantRepository {

    suspend fun addRestaurant(restaurantModel: RestaurantModel)

    suspend fun getAllRestaurants(): RestaurantsListModel

    suspend fun getRestaurantById(restaurantId: UUID): RestaurantModel?

    suspend fun getRestaurantByName(restaurantName: String): RestaurantModel?

    suspend fun updateRestaurant(restaurantModel: RestaurantModel, restaurantAdminId: UUID)

    suspend fun deleteRestaurant(restaurantId: UUID, restaurantAdminId: UUID)
}