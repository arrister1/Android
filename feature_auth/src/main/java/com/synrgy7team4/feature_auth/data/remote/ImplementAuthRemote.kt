package com.synrgy7team4.feature_auth.data.remote

import com.synrgy7team4.feature_auth.data.remote.request.ForgotPasswordRequest
import com.synrgy7team4.feature_auth.data.remote.request.LoginRequest
import com.synrgy7team4.feature_auth.data.remote.retrofit.ApiService
import com.synrgy7team4.feature_auth.data.remote.response.RegistResponse
import com.synrgy7team4.feature_auth.data.remote.request.RegisterRequest
import com.synrgy7team4.feature_auth.data.remote.response.ForgotPasswordResponse
import com.synrgy7team4.feature_auth.data.remote.response.LoginResponse

class ImplementAuthRemote (
    private val apiService: ApiService
) : AuthRemoteSource {



    override suspend fun register(registerRequest: RegisterRequest): RegistResponse {
        return apiService.register(registerRequest)
    }

    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return apiService.login(loginRequest)
    }

    override suspend fun sendForgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse {
        return apiService.sendForgotPassword(forgotPasswordRequest)
    }

    override suspend fun validateForgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse {
        return apiService.validateForgotPassword(forgotPasswordRequest)
    }

    override suspend fun changePasswordForgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse {
        return apiService.changePasswordForgotPassword(forgotPasswordRequest)
    }


}