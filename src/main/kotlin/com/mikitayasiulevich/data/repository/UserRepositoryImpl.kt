package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.data.model.getRoleByString
import com.mikitayasiulevich.data.model.getStringByRole
import com.mikitayasiulevich.data.model.tables.UserTable
import com.mikitayasiulevich.domain.repository.UserRepository
import com.mikitayasiulevich.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class UserRepositoryImpl : UserRepository {
    override suspend fun getUserByEmail(email: String): UserModel? {
        return dbQuery {
            UserTable.selectAll().where { UserTable.email.eq(email) }
                .map { rowToUser(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun insertUser(userModel: UserModel) {
        return dbQuery {
            UserTable.insert { table ->
                table[email] = userModel.email
                table[login] = userModel.login
                table[password] = userModel.password
                table[firstName] = userModel.firstName
                table[lastName] = userModel.lastName
                table[isActive] = userModel.isActive
                table[role] = userModel.role.getStringByRole()
            }
        }
    }

    private fun rowToUser(row: ResultRow?): UserModel? {
        if (row == null)
            return null
        return UserModel(
            id = row[UserTable.id],
            email = row[UserTable.email],
            login = row[UserTable.login],
            password = row[UserTable.password],
            firstName = row[UserTable.firstName],
            lastName = row[UserTable.lastName],
            isActive = row[UserTable.isActive],
            role = row[UserTable.role].getRoleByString()
        )
    }

}