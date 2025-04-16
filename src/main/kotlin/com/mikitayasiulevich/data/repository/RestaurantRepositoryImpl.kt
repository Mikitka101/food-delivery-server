package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.model.RestaurantDBModel
import com.mikitayasiulevich.data.database.tables.RestaurantTable
import com.mikitayasiulevich.data.model.requests.CreateRestaurantRequest
import com.mikitayasiulevich.domain.repository.RestaurantRepository
import com.mikitayasiulevich.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class RestaurantRepositoryImpl : RestaurantRepository {

    override suspend fun addRestaurant(restaurantDBModel: RestaurantDBModel) {
        dbQuery {
            RestaurantTable.insert { table ->
                table[id] = restaurantDBModel.id
                table[admin] = restaurantDBModel.admin
                table[photo] = restaurantDBModel.photo
                table[description] = restaurantDBModel.description
                //table[category] = restaurantDBModel.category
                table[address] = restaurantDBModel.address
                table[name] = restaurantDBModel.name
                table[active] = true
                table[banned] = false
            }
        }
    }

    override suspend fun getAllRestaurants(): List<RestaurantDBModel> {
        return dbQuery {
            RestaurantTable.selectAll().mapNotNull { rowToRestaurant(it) }
        }
    }

    override suspend fun getRestaurantById(restaurantId: UUID): RestaurantDBModel? {
        return dbQuery {
            RestaurantTable.selectAll().where { RestaurantTable.id.eq(restaurantId) }
                .map { rowToRestaurant(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun getRestaurantByName(restaurantName: String): RestaurantDBModel? {
        return dbQuery {
            RestaurantTable.selectAll().where { RestaurantTable.name.eq(restaurantName) }
                .map { rowToRestaurant(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun getRestaurantByAdmin(adminId: UUID): RestaurantDBModel? {
        return dbQuery {
            RestaurantTable.selectAll().where { RestaurantTable.admin.eq(adminId) }
                .map { rowToRestaurant(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun updateRestaurant(restaurantDBModel: RestaurantDBModel, adminId: UUID) {

        val foundRestaurant = getRestaurantByAdmin(adminId) ?: return

        dbQuery {
            RestaurantTable.update(where = {
                RestaurantTable.id.eq(foundRestaurant.id)
            }) { table ->
                table[photo] = restaurantDBModel.photo
                table[description] = restaurantDBModel.description
                //table[category] = restaurantDBModel.category
                table[name] = restaurantDBModel.name
                table[banned] = false
            }
        }
    }

    override suspend fun deleteRestaurant(restaurantId: UUID) {
        dbQuery {
            RestaurantTable.deleteWhere { id.eq(restaurantId) }
        }
    }

    private fun rowToRestaurant(row: ResultRow?): RestaurantDBModel? {
        if (row == null) return null

        return RestaurantDBModel(
            id = row[RestaurantTable.id],
            admin = row[RestaurantTable.admin],
            photo = row[RestaurantTable.photo],
            description = row[RestaurantTable.description],
            address = row[RestaurantTable.address],
            //category = row[RestaurantTable.category],
            name = row[RestaurantTable.name],
            active = row[RestaurantTable.active],
            banned = row[RestaurantTable.banned],
        )
    }
}