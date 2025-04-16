package com.mikitayasiulevich.domain.usecase

import com.mikitayasiulevich.data.model.DishDBModel
import com.mikitayasiulevich.data.model.requests.CreateDishRequest
import com.mikitayasiulevich.domain.model.Dish
import com.mikitayasiulevich.domain.repository.*
import java.util.*

class DishUseCase(
    private val dishRepository: DishRepository,
    private val restaurantRepository: RestaurantRepository,
    private val photoRepository: PhotoRepository,
    private val descriptionRepository: DescriptionRepository,
) {
    suspend fun createDish(createDishRequest: CreateDishRequest, adminId: UUID): DishDBModel? {
        val foundRestaurant = restaurantRepository.getRestaurantByAdmin(adminId) ?: return null
        val description = descriptionRepository.addDescription(createDishRequest.description)
        return try {
            val dishDBModel = createDishRequest.toModel(
                id = UUID.randomUUID(),
                restaurantId = foundRestaurant.id,
                descriptionId = description.id,
            )
            dishRepository.addDish(dishDBModel)
            dishDBModel
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getRestaurantDishes(restaurantId: String): List<Dish> {
        return dishRepository.getRestaurantDishes(UUID.fromString(restaurantId)).map {
            it.toEntity(
                photoRepository.getPhotoById(it.photo).url,
                descriptionRepository.getDescriptionById(it.description).text,
            )
        }
    }

    suspend fun getDishById(dishId: UUID) = dishRepository.getDishById(dishId)

    suspend fun updateDish(createDishRequest: CreateDishRequest, adminId: UUID) {
        val description = descriptionRepository.addDescription(createDishRequest.description)
        dishRepository.updateDish(
            createDishRequest.toUpdateModel(
                descriptionId = description.id
            ), adminId = adminId
        )
    }

    suspend fun deleteDish(dishId: UUID) = dishRepository.deleteDish(dishId)

    private fun CreateDishRequest.toModel(
        id: UUID,
        restaurantId: UUID,
        descriptionId: UUID,
    ): DishDBModel =
        DishDBModel(
            id = id,
            restaurant = restaurantId,
            photo = UUID.fromString(photo),
            description = descriptionId,
            name = this.name,
            price = this.price,
            weight = 0,
            active = true,
            banned = true
        )

    private fun CreateDishRequest.toUpdateModel(
        descriptionId: UUID,
    ): DishDBModel =
        DishDBModel(
            id = UUID.randomUUID(), //Изменить нельзя
            restaurant = UUID.randomUUID(), // Изменить нельзя
            photo = UUID.fromString(photo),
            description = descriptionId,
            name = this.name,
            price = this.price,
            weight = 0,
            active = true,
            banned = true
        )

    private fun DishDBModel.toEntity(
        photo: String,
        description: String,
    ): Dish =
        Dish(
            id = id.toString(),
            restaurant = restaurant.toString(),
            photo = photo,
            description = description,
            name = this.name,
            price = this.price,
            active = active,
            banned = banned
        )
}