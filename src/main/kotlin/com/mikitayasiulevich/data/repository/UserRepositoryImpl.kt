package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.model.UserDBModel
import com.mikitayasiulevich.data.database.tables.UserTable
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

    override suspend fun getAllUsers(): List<UserDBModel> {
        val foundUsersList = dbQuery {
            UserTable.selectAll().mapNotNull {
                rowToUser(row = it)
            }
        }
        val usersWithRoles = mutableListOf<UserDBModel>()
        foundUsersList.forEach { user ->
            usersWithRoles.add(user.copy(roles = roleRepository.getUserRoles(user.id)))
        }
        return usersWithRoles
    }

    override suspend fun getUserById(id: UUID): UserDBModel? {
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

    override suspend fun getUserByLogin(login: String): UserDBModel? {
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

    override suspend fun insertUser(userDBModel: UserDBModel) {
        dbQuery {
            UserTable.insert { table ->
                table[id] = userDBModel.id
                table[login] = userDBModel.login
                table[name] = userDBModel.name
                table[password] = userDBModel.password
                //table[address] = userDBModel.address
                table[banned] = userDBModel.banned
            }
        }
        userDBModel.roles.forEach {
            roleRepository.addRoleToUser(userDBModel.id, it)
        }
    }

    private fun rowToUser(row: ResultRow?): UserDBModel? {
        if (row == null)
            return null
        return UserDBModel(
            id = row[UserTable.id],
            login = row[UserTable.login],
            password = row[UserTable.password],
            name = row[UserTable.name],
            banned = row[UserTable.banned],
            //address = row[UserTable.address],
            roles = emptyList()
        )
    }

}