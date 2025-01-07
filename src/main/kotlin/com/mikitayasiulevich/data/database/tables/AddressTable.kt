package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object AddressTable : Table() {
    val id: Column<UUID> = uuid("id")
    val address: Column<String> = varchar("address", 150)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}