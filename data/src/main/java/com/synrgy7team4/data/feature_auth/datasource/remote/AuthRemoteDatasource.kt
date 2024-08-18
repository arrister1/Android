package com.synrgy7team4.data.feature_auth.datasource.remote

import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterResponse
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest

interface AuthRemoteDatasource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse
}