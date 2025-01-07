package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.database.tables.AddressTable
import com.mikitayasiulevich.data.model.AddressDBModel
import com.mikitayasiulevich.data.model.DescriptionDBModel
import com.mikitayasiulevich.plugins.DatabaseFactory
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import java.util.*

interface DescriptionRepository {
    suspend fun addDescription(description: String): DescriptionDBModel

    suspend fun getDescriptionById(id: UUID): DescriptionDBModel

    suspend fun getEmptyDescriptionId(): UUID

    suspend fun getEmptyDescription(): DescriptionDBModel
}