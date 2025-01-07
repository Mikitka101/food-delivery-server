package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.DishDBModel
import com.mikitayasiulevich.data.model.RestaurantDBModel
import com.mikitayasiulevich.data.model.requests.CreateRestaurantRequest
import java.util.*

interface DishRepository {

    suspend fun addDish(dishDBModel: DishDBModel)

    suspend fun getRestaurantDishes(restaurantId: UUID): List<DishDBModel>

    suspend fun getDishById(dishId: UUID): DishDBModel?

    suspend fun updateDish(dishDBModel: DishDBModel, adminId: UUID)

    suspend fun deleteDish(dishId: UUID)
}