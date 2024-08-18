package com.synrgy7team4.domain.feature_auth.usecase

import com.synrgy7team4.domain.feature_auth.repository.AuthRepository

class TokenUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun saveTokens(jwtToken: String, refreshToken: String) =
        authRepository.saveTokens(jwtToken, refreshToken)

    suspend fun loadJwtToken(): String? =
        authRepository.loadJwtToken()

    suspend fun deleteJwtToken() =
        authRepository.deleteJwtToken()
}