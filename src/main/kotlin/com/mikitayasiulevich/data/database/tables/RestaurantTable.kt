package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object RestaurantTable: Table() {
    val id: Column<UUID> = uuid("id")
    val admin: Column<UUID> = uuid("admin").references(UserTable.id).uniqueIndex()
    val photo: Column<UUID> = uuid("photo").references(PhotoTable.id)
    val description: Column<UUID> = uuid("description").references(DescriptionTable.id)
    val address: Column<UUID> = uuid("address").references(AddressTable.id)
    val name: Column<String> = varchar("name", 50)
    val active: Column<Boolean> = bool("active")
    val banned: Column<Boolean> = bool("banned")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}