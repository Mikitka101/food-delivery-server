package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object OrderDishesTable : Table() {
    val id: Column<UUID> = uuid("id")
    val order: Column<UUID> = uuid("order").references(OrderTable.id)
    val dish: Column<UUID> = uuid("dish").references(DishTable.id)
    val amount: Column<Int> = integer("amount")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}