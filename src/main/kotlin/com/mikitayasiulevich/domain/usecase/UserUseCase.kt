package com.mikitayasiulevich.domain.usecase

import com.auth0.jwt.interfaces.JWTVerifier
import com.mikitayasiulevich.authentification.JwtService
import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.domain.repository.UserRepository

class UserUseCase(
    private val repository: UserRepository,
    private val jwtService: JwtService
) {

    suspend fun createUser(userModel: UserModel) = repository.insertUser(userModel = userModel)

    suspend fun findUserByEmail(email: String) = repository.getUserByEmail(email = email)

    fun generateToken(userModel: UserModel): String = jwtService.generateToken(user = userModel)

    fun getJwtVerifier(): JWTVerifier = jwtService.getVerifier()

}