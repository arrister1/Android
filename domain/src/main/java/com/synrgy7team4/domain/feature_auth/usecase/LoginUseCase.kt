package com.synrgy7team4.domain.feature_auth.usecase

import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.response.LoginResponseDomain
import com.synrgy7team4.domain.feature_auth.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun login(loginRequest: LoginRequest): LoginResponseDomain =
        authRepository.login(loginRequest)
}