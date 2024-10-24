package com.mikitayasiulevich.domain.usecase

import com.auth0.jwt.interfaces.DecodedJWT
import com.mikitayasiulevich.auth.JwtService
import com.mikitayasiulevich.auth.hash
import com.mikitayasiulevich.data.model.UserModel
import com.mikitayasiulevich.data.model.requests.LoginRequest
import com.mikitayasiulevich.data.model.responses.AuthResponse
import com.mikitayasiulevich.domain.repository.RefreshTokenRepository
import com.mikitayasiulevich.domain.repository.UserRepository
import java.util.*

class UserUseCase(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtService: JwtService
) {

    val hashFunction = { p: String -> hash(password = p) }

    suspend fun findAllUsers(): List<UserModel> =
        userRepository.getAllUsers()

    suspend fun findUserById(id: String): UserModel? =
        userRepository.getUserById(
            id = UUID.fromString(id)
        )

    suspend fun createUser(userModel: UserModel): UserModel? {
        val foundUser = userRepository.getUserByLogin(userModel.login)
        return if (foundUser == null) {
            userRepository.insertUser(userModel.copy(password = hashFunction(userModel.password)))
            userModel
        } else null
    }

    suspend fun authenticate(loginRequest: LoginRequest): AuthResponse? {
        val login = loginRequest.login
        val foundUser: UserModel? = userRepository.getUserByLogin(login)

        return if (foundUser != null && hashFunction(loginRequest.password) == foundUser.password) {
            val accessToken = jwtService.createAccessToken(login)
            val refreshToken = jwtService.createRefreshToken(login)

            refreshTokenRepository.save(refreshToken, login)

            AuthResponse(
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
        } else
            null
    }

    suspend fun refreshToken(token: String): String? {
        val decodedRefreshToken = verifyRefreshToken(token)
        val persistedLogin = refreshTokenRepository.findLoginByToken(token)

        return if (decodedRefreshToken != null && persistedLogin != null) {
            val foundUser: UserModel? = userRepository.getUserByLogin(persistedLogin)
            val loginFromRefreshToken: String? = decodedRefreshToken.getClaim("login").asString()

            if (foundUser != null && loginFromRefreshToken == foundUser.login)
                jwtService.createAccessToken(persistedLogin)
            else
                null
        } else
            null
    }

    private fun verifyRefreshToken(token: String): DecodedJWT? {
        val decodedJwt: DecodedJWT? = getDecodedJwt(token)

        return decodedJwt?.let {
            val audienceMatches = jwtService.audienceMatches(it.audience.first())

            if (audienceMatches)
                decodedJwt
            else
                null
        }
    }

    private fun getDecodedJwt(token: String): DecodedJWT? =
        try {
            jwtService.jwtVerifier.verify(token)
        } catch (ex: Exception) {
            null
        }
}