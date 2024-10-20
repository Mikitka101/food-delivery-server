package com.mikitayasiulevich.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val email: Column<String> = varchar("email", 100).uniqueIndex()
    val login: Column<String> = varchar("login", 50).uniqueIndex()
    val password: Column<String> = varchar("password", 50)
    val firstName: Column<String> = varchar("firstName", 30)
    val lastName: Column<String> = varchar("lastName", 30)
    val role: Column<String> = varchar("user_role", 15)
    val isActive: Column<Boolean> = bool("is_active")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}