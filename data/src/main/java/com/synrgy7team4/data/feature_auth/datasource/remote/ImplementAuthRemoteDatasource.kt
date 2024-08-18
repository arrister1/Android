package com.synrgy7team4.data.feature_auth.datasource.remote

import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginErrorResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.retrofit.ApiService
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.usecase.HttpExceptionUseCase
import com.google.gson.Gson
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterErrorResponse
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import retrofit2.HttpException

class ImplementAuthRemoteDatasource(
    private val apiService: ApiService
) : AuthRemoteDatasource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        try {
            apiService.login(loginRequest)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(json, LoginErrorResponse::class.java)
            throw HttpExceptionUseCase(e, error.errors)
        }

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        try {
            apiService.register(registerRequest)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(json, RegisterErrorResponse::class.java)
            throw HttpExceptionUseCase(e, error.message)
        }
}