package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.data.model.tables.UserTable
import com.mikitayasiulevich.domain.repository.UserRepository
import com.mikitayasiulevich.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import java.util.*

class UserRepositoryImpl : UserRepository {

    override suspend fun getAllUsers(): List<UserModel> {
        return dbQuery {
            UserTable.selectAll().mapNotNull {
                rowToUser(row = it)
            }
        }
    }

    override suspend fun getUserById(id: UUID): UserModel? {
        return dbQuery {
            UserTable.selectAll().where { UserTable.id.eq(id) }
                .map { rowToUser(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun getUserByLogin(login: String): UserModel? {
        return dbQuery {
            UserTable.selectAll().where { UserTable.login.eq(login) }
                .map { rowToUser(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun insertUser(userModel: UserModel) {
        return dbQuery {
            UserTable.insert { table ->
                table[id] = userModel.id
                table[login] = userModel.login
                table[password] = userModel.password
            }
        }
    }

    private fun rowToUser(row: ResultRow?): UserModel? {
        if (row == null)
            return null
        return UserModel(
            id = row[UserTable.id],
            login = row[UserTable.login],
            password = row[UserTable.password]
        )
    }

}