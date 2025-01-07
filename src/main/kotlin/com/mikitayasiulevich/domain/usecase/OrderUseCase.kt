package com.mikitayasiulevich.domain.usecase

import com.mikitayasiulevich.data.model.OrderDBModel
import com.mikitayasiulevich.data.model.RestaurantDBModel
import com.mikitayasiulevich.data.model.requests.CreateRestaurantRequest
import com.mikitayasiulevich.domain.model.Restaurant
import com.mikitayasiulevich.domain.repository.*
import java.util.*

class OrderUseCase(
    private val restaurantRepository: RestaurantRepository,
    private val photoRepository: PhotoRepository,
    private val addressRepository: AddressRepository,
    private val descriptionRepository: DescriptionRepository,
) {
    suspend fun createOrder(createOrderRequest: CreateOrderRequest): OrderDBModel? {
        /*val foundRestaurant = restaurantRepository.getRestaurantByName(createRestaurantRequest.name)
        val address = addressRepository.addAddress(createRestaurantRequest.address)
        val description = descriptionRepository.addDescription(createRestaurantRequest.description)
        val category = categoryRepository.addCategory(createRestaurantRequest.description)
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
        } else null*/
    }

    suspend fun getRestaurantOrders(): List<Orders> {
        /*return restaurantRepository.getAllRestaurants().map {
            it.toEntity(
                photoRepository.getPhotoById(it.photo).url,
                descriptionRepository.getDescriptionById(it.description).text,
                addressRepository.getAddressById(it.address).address,
                //categoryRepository.getCategoryById(it.category).name
            )
        }*/
    }

    suspend fun getOrderById(restaurantId: UUID): Restaurant? {
        val foundData = restaurantRepository.getRestaurantById(restaurantId) ?: return null
        return foundData.toEntity(
            photoRepository.getPhotoById(foundData.photo).url,
            descriptionRepository.getDescriptionById(foundData.description).text,
            addressRepository.getAddressById(foundData.address).address,
            //categoryRepository.getCategoryById(foundData.category).name
        )
    }

    suspend fun updateOrder(createRestaurantRequest: CreateRestaurantRequest, adminId: UUID) {
        val address = addressRepository.addAddress(createRestaurantRequest.address)
        val description = descriptionRepository.addDescription(createRestaurantRequest.description)
        val category = categoryRepository.addCategory(createRestaurantRequest.description)
        restaurantRepository.updateRestaurant(
            createRestaurantRequest.toUpdateModel(
                descriptionId = description.id,
                addressId = address.id,
                //categoryId = category.id
            ), adminId = adminId
        )
    }

    suspend fun deleteOrder(restaurantId: UUID) =
        restaurantRepository.deleteRestaurant(restaurantId)

}