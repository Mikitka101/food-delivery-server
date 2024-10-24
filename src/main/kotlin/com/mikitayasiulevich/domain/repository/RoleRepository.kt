package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.RoleModel
import java.util.*

interface RoleRepository {

    suspend fun addRoleToUser(userId: UUID, role: RoleModel)

    suspend fun getUserRoles(userId: UUID): List<RoleModel>

}