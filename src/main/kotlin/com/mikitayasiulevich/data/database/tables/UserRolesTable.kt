package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserRolesTable : Table() {
    val user: Column<UUID> = uuid("user").references(UserTable.id)
    val role: Column<Int> = integer("role").references(RoleTable.id)

    override val primaryKey: PrimaryKey = PrimaryKey(user, role)
}