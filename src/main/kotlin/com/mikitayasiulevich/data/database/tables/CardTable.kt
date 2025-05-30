package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object CardTable: Table() {
    val id: Column<UUID> = uuid("id")
    val number_masked: Column<String> = varchar("number_masked", 16)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}