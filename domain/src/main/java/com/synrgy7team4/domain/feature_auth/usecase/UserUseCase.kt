package com.synrgy7team4.domain.feature_auth.usecase

import com.synrgy7team4.domain.feature_auth.model.response.UserGetResponseDomain
import com.synrgy7team4.domain.feature_auth.repository.UserRepository

class UserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun getUserData(jwtToken: String): UserGetResponseDomain =
        userRepository.getUserData(jwtToken)
}