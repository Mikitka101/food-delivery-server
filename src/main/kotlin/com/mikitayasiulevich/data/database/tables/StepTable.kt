package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object StepTable : Table() {
    val id: Column<Int> = integer("id")
    val step: Column<String> = varchar("step", 50)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}