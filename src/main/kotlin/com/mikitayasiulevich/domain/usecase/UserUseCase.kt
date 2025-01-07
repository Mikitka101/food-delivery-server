package com.mikitayasiulevich.domain.usecase

import com.auth0.jwt.interfaces.DecodedJWT
import com.mikitayasiulevich.auth.JwtService
import com.mikitayasiulevich.auth.hash
import com.mikitayasiulevich.data.model.RoleModel
import com.mikitayasiulevich.data.model.UserDBModel
import com.mikitayasiulevich.data.model.getStringByRole
import com.mikitayasiulevich.data.model.requests.LoginRequest
import com.mikitayasiulevich.data.model.requests.RegisterRequest
import com.mikitayasiulevich.data.model.responses.AuthResponse
import com.mikitayasiulevich.data.model.responses.UserResponse
import com.mikitayasiulevich.domain.repository.AddressRepository
import com.mikitayasiulevich.domain.repository.RefreshTokenRepository
import com.mikitayasiulevich.domain.repository.UserRepository
import java.util.*

class UserUseCase(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val addressRepository: AddressRepository,
    private val jwtService: JwtService
) {

    val hashFunction = { p: String -> hash(password = p) }

    suspend fun findAllUsers(): List<UserDBModel> =
        userRepository.getAllUsers()

    suspend fun findUserById(id: String): UserDBModel? =
        userRepository.getUserById(
            id = UUID.fromString(id)
        )

    suspend fun findUserByLogin(login: String): UserDBModel? =
        userRepository.getUserByLogin(
            login
        )

    suspend fun createUser(registerRequest: RegisterRequest): UserDBModel? {
        val foundUser = userRepository.getUserByLogin(registerRequest.login)

        return if (foundUser == null) {
            val userDBModel = registerRequest.toModel()
            userRepository.insertUser(userDBModel.copy(password = hashFunction(userDBModel.password)))
            userDBModel
        } else null
    }

    suspend fun authenticate(loginRequest: LoginRequest): AuthResponse? {
        val login = loginRequest.login
        val foundUser: UserDBModel? = userRepository.getUserByLogin(login)

        return if (foundUser != null && hashFunction(loginRequest.password) == foundUser.password) {
            val accessToken = jwtService.createAccessToken(login,
                foundUser.roles.joinToString(", ") { it.getStringByRole() })
            val refreshToken =
                jwtService.createRefreshToken(login, foundUser.roles.joinToString(", ") { it.getStringByRole() })

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
            val foundUser: UserDBModel? = userRepository.getUserByLogin(persistedLogin)
            val loginFromRefreshToken: String? = decodedRefreshToken.getClaim("login").asString()

            if (foundUser != null && loginFromRefreshToken == foundUser.login)
                jwtService.createAccessToken(persistedLogin, foundUser.roles.joinToString(", ") { it.getStringByRole() })
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

    private fun RegisterRequest.toModel(): UserDBModel =
        UserDBModel(
            id = UUID.randomUUID(),
            login = this.login,
            password = this.password,
            name = this.name,
            //address = address,
            roles = listOf(RoleModel.CLIENT),
            banned = false
        )

    private fun UserDBModel.toResponse(): UserResponse =
        UserResponse(
            id = this.id,
            login = this.login,
            name = this.name,
            roles = this.roles.map { it.getStringByRole() }
        )
}