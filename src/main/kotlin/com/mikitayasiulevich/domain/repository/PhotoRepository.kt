package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.database.tables.AddressTable
import com.mikitayasiulevich.data.model.AddressDBModel
import com.mikitayasiulevich.data.model.PhotoDBModel
import com.mikitayasiulevich.plugins.DatabaseFactory
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import java.util.*

interface PhotoRepository {
    suspend fun addPhoto(address: String): PhotoDBModel

    suspend fun getPhotoById(id: UUID): PhotoDBModel

    suspend fun getDefaultPhoto(): PhotoDBModel
}