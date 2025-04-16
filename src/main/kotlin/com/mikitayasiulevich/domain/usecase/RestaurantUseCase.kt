package com.mikitayasiulevich.domain.usecase

import com.mikitayasiulevich.data.model.RestaurantDBModel
import com.mikitayasiulevich.data.model.requests.CreateRestaurantRequest
import com.mikitayasiulevich.domain.model.Restaurant
import com.mikitayasiulevich.domain.repository.*
import java.util.*

class RestaurantUseCase(
    private val restaurantRepository: RestaurantRepository,
    private val photoRepository: PhotoRepository,
    private val addressRepository: AddressRepository,
    private val descriptionRepository: DescriptionRepository,
) {
    suspend fun createRestaurant(createRestaurantRequest: CreateRestaurantRequest, adminId: UUID): RestaurantDBModel? {
        val foundRestaurant = restaurantRepository.getRestaurantByName(createRestaurantRequest.name)
        val address = addressRepository.addAddress(createRestaurantRequest.address)
        val description = descriptionRepository.addDescription(createRestaurantRequest.description)
        //val category = categoryRepository.addCategory(createRestaurantRequest.description)
        return if (foundRestaurant == null) {
            val restaurantDBModel = createRestaurantRequest.toModel(
                id = UUID.randomUUID(),
                adminId = adminId,
                descriptionId = description.id,
                addressId = address.id,
                //categoryId = category.id
            )
            restaurantRepository.addRestaurant(restaurantDBModel)
            restaurantDBModel
        } else null
    }

    suspend fun getAllRestaurants(): List<Restaurant> {
        return restaurantRepository.getAllRestaurants().map {
            it.toEntity(
                photoRepository.getPhotoById(it.photo).url,
                descriptionRepository.getDescriptionById(it.description).text,
                addressRepository.getAddressById(it.address).address,
                //categoryRepository.getCategoryById(it.category).name
            )
        }
    }

    suspend fun getRestaurantById(restaurantId: UUID): Restaurant? {
        val foundData = restaurantRepository.getRestaurantById(restaurantId) ?: return null
        return foundData.toEntity(
            photoRepository.getPhotoById(foundData.photo).url,
            descriptionRepository.getDescriptionById(foundData.description).text,
            addressRepository.getAddressById(foundData.address).address,
            //categoryRepository.getCategoryById(foundData.category).name
        )
    }

    suspend fun updateRestaurant(createRestaurantRequest: CreateRestaurantRequest, adminId: UUID) {
        val address = addressRepository.addAddress(createRestaurantRequest.address)
        val description = descriptionRepository.addDescription(createRestaurantRequest.description)
        //val category = categoryRepository.addCategory(createRestaurantRequest.description)
        restaurantRepository.updateRestaurant(
            createRestaurantRequest.toUpdateModel(
                descriptionId = description.id,
                addressId = address.id,
                //categoryId = category.id
            ), adminId = adminId
        )
    }

    suspend fun deleteRestaurant(restaurantId: UUID) =
        restaurantRepository.deleteRestaurant(restaurantId)

    private fun CreateRestaurantRequest.toModel(
        id: UUID,
        adminId: UUID,
        descriptionId: UUID,
        addressId: UUID,
        //categoryId: UUID
    ): RestaurantDBModel =
        RestaurantDBModel(
            id = id,
            admin = adminId,
            photo = UUID.fromString(photo),
            description = descriptionId,
            address = addressId,
            //category = categoryId,
            name = this.name,
            active = true,
            banned = true
        )

    private fun CreateRestaurantRequest.toUpdateModel(
        descriptionId: UUID,
        addressId: UUID,
        //categoryId: UUID
    ): RestaurantDBModel =
        RestaurantDBModel(
            id = UUID.randomUUID(), //Изменить нельзя
            admin = UUID.randomUUID(), // Изменить нельзя
            photo = UUID.fromString(photo),
            description = descriptionId,
            address = addressId,
            //category = categoryId,
            name = this.name,
            active = true,
            banned = true
        )

    private fun RestaurantDBModel.toEntity(
        photo: String,
        description: String,
        address: String,
        //category: String
    ): Restaurant =
        Restaurant(
            id = id.toString(),
            admin = admin.toString(),
            photo = photo,
            description = description,
            address = address,
            //category = category,
            name = this.name,
            active = active,
            banned = banned
        )
}