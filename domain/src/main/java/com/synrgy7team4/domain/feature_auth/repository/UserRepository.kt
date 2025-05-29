package com.synrgy7team4.domain.feature_auth.repository

import com.synrgy7team4.domain.feature_auth.model.response.UserGetResponseDomain

interface UserRepository {
    suspend fun getUserData(jwtToken: String): UserGetResponseDomain
}