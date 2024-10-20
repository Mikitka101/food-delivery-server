package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.UserModel

interface UserRepository {

    suspend fun getUserByEmail(email: String): UserModel?

    suspend fun insertUser(userModel: UserModel)
}