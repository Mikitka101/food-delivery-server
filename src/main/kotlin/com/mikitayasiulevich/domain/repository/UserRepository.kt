package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.UserDBModel
import java.util.*

interface UserRepository {

    suspend fun getAllUsers(): List<UserDBModel>

    suspend fun getUserById(id: UUID): UserDBModel?

    suspend fun getUserByLogin(login: String): UserDBModel?

    suspend fun insertUser(userDBModel: UserDBModel)
}