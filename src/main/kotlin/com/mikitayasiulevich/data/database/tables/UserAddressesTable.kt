package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserAddressesTable : Table() {
    val user: Column<UUID> = uuid("user").references(UserTable.id)
    val address: Column<UUID> = uuid("address").references(AddressTable.id)

    override val primaryKey: PrimaryKey = PrimaryKey(user, address)
}