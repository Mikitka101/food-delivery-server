package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.database.tables.DishTable
import com.mikitayasiulevich.data.model.RestaurantDBModel
import com.mikitayasiulevich.data.database.tables.RestaurantTable
import com.mikitayasiulevich.data.model.CategoryDBModel
import com.mikitayasiulevich.data.model.DishDBModel
import com.mikitayasiulevich.data.model.requests.CreateRestaurantRequest
import com.mikitayasiulevich.domain.repository.DishRepository
import com.mikitayasiulevich.domain.repository.RestaurantRepository
import com.mikitayasiulevich.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class DishRepositoryImpl : DishRepository {

    override suspend fun addDish(dishDBModel: DishDBModel) {
        dbQuery {
            DishTable.insert { table ->
                table[id] = dishDBModel.id
                table[restaurant] = dishDBModel.restaurant
                table[photo] = dishDBModel.photo
                table[description] = dishDBModel.description
                table[name] = dishDBModel.name
                table[price] = dishDBModel.price
                table[active] = true
                table[banned] = false
            }
        }
    }

    override suspend fun getRestaurantDishes(restaurantId: UUID): List<DishDBModel> {
        return dbQuery {
            DishTable.selectAll().where { DishTable.restaurant.eq(restaurantId) }.mapNotNull { rowToDish(it) }
        }
    }

    override suspend fun getDishById(dishId: UUID): DishDBModel? {
        return dbQuery {
            DishTable.selectAll().where { DishTable.id.eq(dishId) }
                .map { rowToDish(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun updateDish(dishDBModel: DishDBModel, adminId: UUID) {

        dbQuery {
            DishTable.update(where = {
                DishTable.id.eq(dishDBModel.id)
            }) { table ->
                table[photo] = dishDBModel.photo
                table[description] = dishDBModel.description
                table[name] = dishDBModel.name
                table[price] = dishDBModel.price
                table[banned] = false
            }
        }
    }

    override suspend fun deleteDish(dishId: UUID) {
        dbQuery {
            DishTable.deleteWhere { id.eq(dishId) }
        }
    }

    private fun rowToDish(row: ResultRow?): DishDBModel? {
        if (row == null) return null

        return DishDBModel(
            id = row[DishTable.id],
            restaurant = row[DishTable.restaurant],
            photo = row[DishTable.photo],
            description = row[DishTable.description],
            name = row[DishTable.name],
            price = row[DishTable.price],
            active = row[DishTable.active],
            banned = row[DishTable.banned],
        )
    }
}