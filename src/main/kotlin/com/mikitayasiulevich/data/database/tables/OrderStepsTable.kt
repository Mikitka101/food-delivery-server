package com.mikitayasiulevich.data.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object OrderStepsTable : Table() {
    val id: Column<UUID> = uuid("id")
    val order: Column<UUID> = uuid("order").references(OrderTable.id)
    val step: Column<Int> = integer("step").references(StepTable.id)
    val step_begin: Column<LocalDateTime> = datetime("step_begin")
    val step_end: Column<LocalDateTime> = datetime("step_end")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}