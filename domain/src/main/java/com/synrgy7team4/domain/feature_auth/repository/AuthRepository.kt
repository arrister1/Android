package com.synrgy7team4.domain.feature_auth.repository

import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.response.LoginDataDomain
import com.synrgy7team4.domain.feature_auth.model.response.LoginResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): LoginResponseDomain
    suspend fun register(registerRequest: RegisterRequest): RegisterResponseDomain
    suspend fun saveTokens(jwtToken: String, refreshToken: String)
    suspend fun loadJwtToken(): String?
    suspend fun deleteJwtToken()
}