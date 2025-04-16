package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.database.tables.DescriptionTable
import com.mikitayasiulevich.data.model.DescriptionDBModel
import com.mikitayasiulevich.domain.repository.DescriptionRepository
import com.mikitayasiulevich.plugins.DatabaseFactory
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class DescriptionRepositoryImpl : DescriptionRepository {

    override suspend fun addDescription(description: String): DescriptionDBModel {
        val newDescriptionId = UUID.randomUUID()
        DatabaseFactory.dbQuery {
            DescriptionTable.insert { table ->
                table[id] = newDescriptionId
                table[DescriptionTable.text] = description
            }
        }
        return DescriptionDBModel(newDescriptionId, description)
    }

    override suspend fun getDescriptionById(id: UUID): DescriptionDBModel {
        return DatabaseFactory.dbQuery {
            DescriptionTable.selectAll().where { DescriptionTable.id.eq(id) }
                .map { rowToDescription(it) }.singleOrNull()
        } ?: getEmptyDescription()
    }

    override suspend fun getEmptyDescriptionId(): UUID {
        return DatabaseFactory.dbQuery {
            DescriptionTable.selectAll().where { DescriptionTable.text.eq("") }
                .map { rowToDescription(it)?.id }.singleOrNull()
        } ?: addDescription("").id
    }

    override suspend fun getEmptyDescription(): DescriptionDBModel {
        return DatabaseFactory.dbQuery {
            DescriptionTable.selectAll().where { DescriptionTable.text.eq("") }
                .map { rowToDescription(it) }.singleOrNull()
        } ?: addDescription("")
    }

    override suspend fun deleteDescriptionById(id: UUID) {
        TODO("Not yet implemented")
    }

    override suspend fun updateDescription(description: DescriptionDBModel) {
        TODO("Not yet implemented")
    }

    private fun rowToDescription(row: ResultRow?): DescriptionDBModel? {
        if (row == null) return null

        return DescriptionDBModel(
            id = row[DescriptionTable.id],
            text = row[DescriptionTable.text],
        )
    }
}