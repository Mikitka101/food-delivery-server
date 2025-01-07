package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.database.tables.PhotoTable
import com.mikitayasiulevich.data.model.PhotoDBModel
import com.mikitayasiulevich.domain.repository.PhotoRepository
import com.mikitayasiulevich.plugins.DatabaseFactory
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class PhotoRepositoryImpl : PhotoRepository {

    override suspend fun addPhoto(url: String): PhotoDBModel {
        val newPhotoId = UUID.randomUUID()
        DatabaseFactory.dbQuery {
            PhotoTable.insert { table ->
                table[id] = newPhotoId
                table[PhotoTable.url] = url
            }
        }
        return PhotoDBModel(
            newPhotoId,
            url
        )
    }

    override suspend fun getPhotoById(id: UUID): PhotoDBModel {
        return DatabaseFactory.dbQuery {
            PhotoTable.selectAll().where { PhotoTable.id.eq(id) }
                .map { rowToPhoto(it) }.singleOrNull()
        }?: getDefaultPhoto()
    }

    override suspend fun getDefaultPhoto(): PhotoDBModel {
        return DatabaseFactory.dbQuery {
            PhotoTable.selectAll().where { PhotoTable.url.eq("https://i.imgur.com/vTKHdSG.jpeg") }
                .map { rowToPhoto(it) }.singleOrNull()
        } ?: addPhoto("https://i.imgur.com/vTKHdSG.jpeg")
    }

    private fun rowToPhoto(row: ResultRow?): PhotoDBModel? {
        if (row == null) return null

        return PhotoDBModel(
            id = row[PhotoTable.id],
            url = row[PhotoTable.url],
        )
    }
}