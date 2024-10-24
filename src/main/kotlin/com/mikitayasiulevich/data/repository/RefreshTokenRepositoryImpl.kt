package com.mikitayasiulevich.data.repository

import com.mikitayasiulevich.domain.repository.RefreshTokenRepository

class RefreshTokenRepositoryImpl: RefreshTokenRepository {

    private val tokens = mutableMapOf<String, String>()

    override fun findLoginByToken(token: String): String? =
        tokens[token]

    override fun save(token: String, username: String) {
        tokens[token] = username
    }

}