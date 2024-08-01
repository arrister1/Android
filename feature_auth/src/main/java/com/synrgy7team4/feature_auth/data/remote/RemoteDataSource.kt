package com.synrgy7team4.feature_auth.data.remote

import com.synrgy7team4.feature_auth.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun login(email: String, password: String) {
        val email = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val password = password.toRequestBody("text/plain".toMediaTypeOrNull())
        apiService.login(email, password)
    }
}