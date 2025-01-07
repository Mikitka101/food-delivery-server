package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.AddressDBModel
import java.util.*

interface AddressRepository {
    suspend fun addAddress(address: String): AddressDBModel

    suspend fun getAddressById(id: UUID): AddressDBModel

    suspend fun getEmptyAddress(): AddressDBModel

    suspend fun getEmptyAddressId(): UUID
}