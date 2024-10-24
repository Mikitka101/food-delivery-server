package com.mikitayasiulevich.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserRolesTable : Table() {
    val userId: Column<UUID> = uuid("user_id").references(UserTable.id)
    val roleId: Column<Int> = integer("role_id")

    override val primaryKey: PrimaryKey = PrimaryKey(userId, roleId)
}