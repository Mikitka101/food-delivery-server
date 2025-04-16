package com.mikitayasiulevich.domain.repository

interface RefreshTokenRepository {

    fun findLoginByToken(token: String): String?

    fun save(token: String, username: String)
}