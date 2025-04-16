package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.AddressDBModel
import com.mikitayasiulevich.data.model.CardDBModel
import java.util.*

interface CardRepository {
    suspend fun addCard(cardNumber: String): CardDBModel

    suspend fun getCardById(id: UUID): CardDBModel

    suspend fun getEmptyCard(): CardDBModel

    suspend fun getEmptyCardId(): UUID

    suspend fun deleteCardById(id: UUID)
}