package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.UserModel
import java.util.*

interface UserRepository {

    suspend fun getAllUsers(): List<UserModel>

    suspend fun getUserById(id: UUID): UserModel?

    suspend fun getUserByLogin(login: String): UserModel?

    suspend fun insertUser(userModel: UserModel)
}