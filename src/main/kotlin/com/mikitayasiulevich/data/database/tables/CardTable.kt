package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object CardTable: Table() {
    val id: Column<UUID> = uuid("id")
    val owner: Column<String> = varchar("owner", 40)
    val number: Column<String> = varchar("number", 16)
    val date: Column<String> = varchar("date", 5)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}