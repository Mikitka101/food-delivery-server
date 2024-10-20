package com.mikitayasiulevich.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object DishTable: Table() {
    val id: Column<Int> = integer("dish_id").autoIncrement()
    val owner: Column<Int> = integer("dish_owner").references(RestaurantTable.id)
    val dishName: Column<String> = varchar("dish_name", 100)
    val dishDescription: Column<String> = varchar("dish_description", 500)
    val dishCreateDate: Column<String> = varchar("dish_create_date", 20)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}