package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.data.model.RoleModel
import com.mikitayasiulevich.data.model.getRoleById
import com.mikitayasiulevich.data.database.tables.UserRolesTable
import com.mikitayasiulevich.domain.repository.RoleRepository
import com.mikitayasiulevich.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class RoleRepositoryImpl : RoleRepository {

    override suspend fun addRoleToUser(userId: UUID, role: RoleModel) {
        dbQuery {
            UserRolesTable.insert { table ->
                table[UserRolesTable.user] = userId
                table[UserRolesTable.role] = role.id
            }
        }
    }

    override suspend fun getUserRoles(userId: UUID): List<RoleModel> {
        return dbQuery {
            UserRolesTable.selectAll().where { UserRolesTable.user.eq(userId) }
                .map { it[UserRolesTable.role].getRoleById() }
        }
    }

}