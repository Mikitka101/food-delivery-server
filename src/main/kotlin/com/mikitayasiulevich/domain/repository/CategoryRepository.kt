package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.database.tables.AddressTable
import com.mikitayasiulevich.data.model.AddressDBModel
import com.mikitayasiulevich.data.model.CategoryDBModel
import com.mikitayasiulevich.data.model.DescriptionDBModel
import com.mikitayasiulevich.plugins.DatabaseFactory
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import java.util.*

interface CategoryRepository {
    suspend fun addCategory(category: String): CategoryDBModel

    suspend fun getCategoryById(id: UUID): CategoryDBModel

     suspend fun getDefaultCategory(): CategoryDBModel

    suspend fun getDefaultCategoryId(): UUID
}