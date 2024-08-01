package com.synrgy7team4.feature_auth.data.remote.retrofit

import com.synrgy7team4.feature_auth.data.remote.response.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("auth/login")
    suspend fun login(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): LoginResponse
}