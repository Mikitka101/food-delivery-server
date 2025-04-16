package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.database.tables.AddressTable
import com.mikitayasiulevich.data.database.tables.RestaurantTable
import com.mikitayasiulevich.data.model.AddressDBModel
import com.mikitayasiulevich.data.model.RestaurantDBModel
import com.mikitayasiulevich.domain.repository.AddressRepository
import com.mikitayasiulevich.plugins.DatabaseFactory
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class AddressRepositoryImpl : AddressRepository {

    override suspend fun addAddress(address: String): AddressDBModel {
        val newAddressId = UUID.randomUUID()
        DatabaseFactory.dbQuery {
            AddressTable.insert { table ->
                table[id] = newAddressId
                table[AddressTable.address] = address
            }
        }
        return AddressDBModel(newAddressId, address)
    }

    override suspend fun getAddressById(id: UUID): AddressDBModel {
        return DatabaseFactory.dbQuery {
            AddressTable.selectAll().where { AddressTable.id.eq(id) }
                .map { rowToAddress(it) }.singleOrNull()
        } ?: getEmptyAddress()
    }

    override suspend fun getEmptyAddressId(): UUID {
        return DatabaseFactory.dbQuery {
            AddressTable.selectAll().where { AddressTable.address.eq("") }
                .map { rowToAddress(it)?.id }.singleOrNull()
        } ?: addAddress("").id
    }

    override suspend fun deleteAddressById(id: UUID) {
        TODO("Not yet implemented")
    }

    override suspend fun getEmptyAddress(): AddressDBModel {
        return DatabaseFactory.dbQuery {
            AddressTable.selectAll().where { AddressTable.address.eq("") }
                .map { rowToAddress(it) }.singleOrNull()
        } ?: addAddress("")
    }

    private fun rowToAddress(row: ResultRow?): AddressDBModel? {
        if (row == null) return null

        return AddressDBModel(
            id = row[AddressTable.id],
            address = row[AddressTable.address],
        )
    }
}