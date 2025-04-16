package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserCardsTable : Table() {
    val user: Column<UUID> = uuid("user").references(UserTable.id)
    val card: Column<UUID> = uuid("card").references(CardTable.id)

    override val primaryKey: PrimaryKey = PrimaryKey(user, card)
}