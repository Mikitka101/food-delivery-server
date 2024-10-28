package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.model.RestaurantModel
import com.mikitayasiulevich.data.model.RestaurantsListModel
import com.mikitayasiulevich.data.model.tables.RestaurantTable
import com.mikitayasiulevich.domain.repository.RestaurantRepository
import com.mikitayasiulevich.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class RestaurantRepositoryImpl : RestaurantRepository {
    override suspend fun addRestaurant(restaurantModel: RestaurantModel) {
        dbQuery {
            RestaurantTable.insert { table ->
                table[id] = restaurantModel.id
                table[restaurantAdmin] = restaurantModel.restaurantAdmin
                table[restaurantName] = restaurantModel.restaurantName
                table[restaurantDescription] = restaurantModel.restaurantDescription
                table[restaurantAddress] = restaurantModel.restaurantAddress
                table[restaurantCreateDate] = restaurantModel.restaurantCreateDate
                table[isVerified] = restaurantModel.isVerified
            }
        }
    }

    override suspend fun getAllRestaurants(): RestaurantsListModel {
        return dbQuery {
            RestaurantsListModel(RestaurantTable.selectAll().mapNotNull { rowToRestaurant(it) })
        }
    }

    override suspend fun getRestaurantById(restaurantId: UUID): RestaurantModel? {
        return dbQuery {
            RestaurantTable.selectAll().where { RestaurantTable.id.eq(restaurantId) }
                .map { rowToRestaurant(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun getRestaurantByName(restaurantName: String): RestaurantModel? {
        return dbQuery {
            RestaurantTable.selectAll().where { RestaurantTable.restaurantName.eq(restaurantName) }
                .map { rowToRestaurant(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun updateRestaurant(restaurantModel: RestaurantModel, restaurantAdminId: UUID) {
        dbQuery {
            RestaurantTable.update(where = {
                RestaurantTable.restaurantAdmin.eq(restaurantAdminId) and RestaurantTable.id.eq(restaurantModel.id)
            }) { table ->
                table[restaurantAdmin] = restaurantAdminId
                table[restaurantName] = restaurantModel.restaurantName
                table[restaurantDescription] = restaurantModel.restaurantDescription
                table[restaurantAddress] = restaurantModel.restaurantAddress
                table[restaurantCreateDate] = restaurantModel.restaurantCreateDate
                table[isVerified] = restaurantModel.isVerified
            }
        }
    }

    override suspend fun deleteRestaurant(restaurantId: UUID, restaurantAdminId: UUID) {
        dbQuery {
            RestaurantTable.deleteWhere { id.eq(restaurantId) and restaurantAdmin.eq(restaurantAdminId) }
        }
    }

    private fun rowToRestaurant(row: ResultRow?): RestaurantModel? {
        if (row == null) return null

        return RestaurantModel(
            id = row[RestaurantTable.id],
            restaurantAdmin = row[RestaurantTable.restaurantAdmin],
            restaurantName = row[RestaurantTable.restaurantName],
            restaurantDescription = row[RestaurantTable.restaurantDescription],
            restaurantAddress = row[RestaurantTable.restaurantAddress],
            restaurantCreateDate = row[RestaurantTable.restaurantCreateDate],
            isVerified = row[RestaurantTable.isVerified],
        )
    }
}