package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object NutritionalValuesTable : Table() {
    val id: Column<UUID> = uuid("id")
    val calories: Column<Int> = integer("calories")
    val proteins: Column<Int> = integer("proteins")
    val fats: Column<Int> = integer("fats")
    val carbohydrates: Column<Int> = integer("carbohydrates")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}