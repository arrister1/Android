package com.synrgy7team4.data.feature_auth.datasource.remote.retrofit

import com.synrgy7team4.data.feature_auth.datasource.remote.response.EmailCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.KtpNumberCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.PhoneNumberCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.UserGetResponse
import com.synrgy7team4.domain.feature_auth.model.request.EmailCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.KtpNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.request.PhoneNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @POST("auth/check-email")
    suspend fun checkEmailAvailability(
        @Body emailCheckRequest: EmailCheckRequest
    ): EmailCheckResponse

    @POST("auth/check-phone-number")
    suspend fun checkPhoneNumberAvailability(
        @Body phoneNumberCheckRequest: PhoneNumberCheckRequest
    ): PhoneNumberCheckResponse

    @POST("auth/check-ktp")
    suspend fun checkKtpNumberAvailability(
        @Body ktpNumberCheckRequest: KtpNumberCheckRequest
    ): KtpNumberCheckResponse

    @GET("user/me")
    suspend fun getUser(
        @Header("Authorization") jwtToken: String
    ): UserGetResponse
}