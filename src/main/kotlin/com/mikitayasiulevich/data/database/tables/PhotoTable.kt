package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object PhotoTable : Table() {
    val id: Column<UUID> = uuid("id")
    val url: Column<String> = varchar("url", 100)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}