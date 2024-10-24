package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.data.model.tables.UserTable
import com.mikitayasiulevich.domain.repository.RoleRepository
import com.mikitayasiulevich.domain.repository.UserRepository
import com.mikitayasiulevich.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class UserRepositoryImpl(
    private val roleRepository: RoleRepository
) : UserRepository {

    override suspend fun getAllUsers(): List<UserModel> {
        val foundUsersList = dbQuery {
            UserTable.selectAll().mapNotNull {
                rowToUser(row = it)
            }
        }
        val usersWithRoles = mutableListOf<UserModel>()
        foundUsersList.forEach { user ->
            usersWithRoles.add(user.copy(roles = roleRepository.getUserRoles(user.id)))
        }
        return usersWithRoles
    }

    override suspend fun getUserById(id: UUID): UserModel? {
        var foundUser = dbQuery {
            UserTable.selectAll().where { UserTable.id.eq(id) }
                .map { rowToUser(row = it) }
                .singleOrNull()
        }
        if (foundUser != null) {
            foundUser = foundUser.copy(roles = roleRepository.getUserRoles(foundUser.id))
        }
        return foundUser
    }

    override suspend fun getUserByLogin(login: String): UserModel? {
        var foundUser = dbQuery {
            UserTable.selectAll().where { UserTable.login.eq(login) }
                .map { rowToUser(row = it) }
                .singleOrNull()
        }
        if (foundUser != null) {
            foundUser = foundUser.copy(roles = roleRepository.getUserRoles(foundUser.id))
        }
        return foundUser
    }

    override suspend fun insertUser(userModel: UserModel) {
        dbQuery {
            UserTable.insert { table ->
                table[id] = userModel.id
                table[login] = userModel.login
                table[password] = userModel.password
            }
        }
        userModel.roles.forEach {
            roleRepository.addRoleToUser(userModel.id, it)
        }
    }

    private fun rowToUser(row: ResultRow?): UserModel? {
        if (row == null)
            return null
        return UserModel(
            id = row[UserTable.id],
            login = row[UserTable.login],
            password = row[UserTable.password],
            roles = emptyList()
        )
    }

}