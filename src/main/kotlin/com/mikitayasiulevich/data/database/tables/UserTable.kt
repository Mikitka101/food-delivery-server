package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserTable: Table() {
    val id: Column<UUID> = uuid("id")
    val login: Column<String> = varchar("login", 50).uniqueIndex()
    val password: Column<String> = varchar("password", 100)
    val name: Column<String> = varchar("name", 30)
    val banned: Column<Boolean> = bool("banned")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}