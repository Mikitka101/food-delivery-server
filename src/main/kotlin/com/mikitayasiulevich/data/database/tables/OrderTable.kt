package com.mikitayasiulevich.data.database.tables

import com.mikitayasiulevich.data.database.tables.DishTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import java.util.*

object OrderTable : Table() {
    val id: Column<UUID> = uuid("id")
    val client: Column<UUID> = uuid("client").references(UserTable.id)
    val courier: Column<UUID> = uuid("courier").references(UserTable.id)
    val restaurant: Column<UUID> = uuid("restaurant").references(RestaurantTable.id)
    val address: Column<UUID> = uuid("address").references(AddressTable.id)
    val card: Column<UUID> = uuid("card").references(CardTable.id)
    val comment: Column<UUID> = uuid("comment").references(DescriptionTable.id)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}