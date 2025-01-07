package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object RoleTable : Table() {
    val id: Column<Int> = integer("id")
    val role: Column<String> = varchar("role", 20)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}