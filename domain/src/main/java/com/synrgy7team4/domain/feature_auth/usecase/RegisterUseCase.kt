package com.synrgy7team4.domain.feature_auth.usecase

import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain
import com.synrgy7team4.domain.feature_auth.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun register(registerRequest: RegisterRequest): RegisterResponseDomain =
        authRepository.register(registerRequest)
}