package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object DescriptionTable : Table() {
    val id: Column<UUID> = uuid("id")
    val text: Column<String> = varchar("text", 250)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}