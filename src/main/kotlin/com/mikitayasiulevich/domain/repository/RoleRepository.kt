package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.Role
import java.util.*

interface RoleRepository {

    suspend fun addRoleToUser(userId: UUID, role: Role)

    suspend fun getUserRoles(userId: UUID): List<Role>

}