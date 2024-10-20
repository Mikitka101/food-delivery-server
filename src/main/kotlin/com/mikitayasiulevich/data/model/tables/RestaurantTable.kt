package com.mikitayasiulevich.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object RestaurantTable: Table() {
    val id: Column<Int> = integer("restaurant_id").autoIncrement()
    val restaurantAdmin: Column<Int> = integer("restaurant_admin").references(UserTable.id)
    val restaurantName: Column<String> = varchar("restaurant_name", 100)
    val restaurantDescription: Column<String> = varchar("restaurant_description", 500)
    val restaurantAddress: Column<String> = varchar("restaurant_address", 70)
    val restaurantCreateDate: Column<String> = varchar("restaurant_create_date", 20)
    val isVerified: Column<Boolean> = bool("is_restaurant_verified")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}