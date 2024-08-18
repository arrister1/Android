package com.synrgy7team4.data.feature_auth.datasource.remote.retrofit

import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterResponse
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse
}