package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object DishTable: Table() {
    val id: Column<UUID> = uuid("id")
    val restaurant: Column<UUID> = uuid("restaurant").references(RestaurantTable.id)
    val photo: Column<UUID> = uuid("photo").references(PhotoTable.id)
    val description: Column<UUID> = uuid("description").references(DescriptionTable.id)
    val name: Column<String> = varchar("name", 100)
    val price: Column<Float> = float("price")
    val active: Column<Boolean> = bool("active")
    val banned: Column<Boolean> = bool("banned")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}