package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.database.tables.CategoryTable
import com.mikitayasiulevich.data.database.tables.PhotoTable
import com.mikitayasiulevich.data.model.CategoryDBModel
import com.mikitayasiulevich.data.model.PhotoDBModel
import com.mikitayasiulevich.domain.repository.CategoryRepository
import com.mikitayasiulevich.plugins.DatabaseFactory
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class CategoryRepositoryImpl : CategoryRepository {

    override suspend fun addCategory(category: String): CategoryDBModel {
        val newDescriptionId = UUID.randomUUID()
        DatabaseFactory.dbQuery {
            CategoryTable.insert { table ->
                table[id] = newDescriptionId
                table[CategoryTable.name] = category
            }
        }
        return CategoryDBModel(newDescriptionId, category)
    }

    override suspend fun getCategoryById(id: UUID): CategoryDBModel {
        return DatabaseFactory.dbQuery {
            CategoryTable.selectAll().where { CategoryTable.id.eq(id) }
                .map { rowToCategory(it) }.singleOrNull()
        } ?: getDefaultCategory()
    }

    override suspend fun getDefaultCategoryId(): UUID {
        return DatabaseFactory.dbQuery {
            CategoryTable.selectAll().where { CategoryTable.name.eq("Food") }
                .map { rowToCategory(it)?.id }.singleOrNull()
        } ?: addCategory("Food").id
    }

    override suspend fun getDefaultCategory(): CategoryDBModel {
        return DatabaseFactory.dbQuery {
            CategoryTable.selectAll().where { CategoryTable.name.eq("Food") }
                .map { rowToCategory(it) }.singleOrNull()
        } ?: addCategory("Food")
    }


    private fun rowToCategory(row: ResultRow?): CategoryDBModel? {
        if (row == null) return null

        return CategoryDBModel(
            id = row[CategoryTable.id],
            name = row[CategoryTable.name],
        )
    }
}