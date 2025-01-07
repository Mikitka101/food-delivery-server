package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import java.util.*

object OrderTable : Table() {
    val id: Column<UUID> = uuid("id")
    val user: Column<UUID> = uuid("user").references(UserTable.id)
    val restaurant: Column<UUID> = uuid("restaurant").references(RestaurantTable.id)
    val address: Column<UUID> = uuid("address").references(AddressTable.id)
    val card: Column<UUID> = uuid("card").references(CardTable.id)
    val status: Column<String> = varchar("status", 15)
    val posted: Column<LocalDate> = date("posted")
    val received: Column<LocalDate> = date("received")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}