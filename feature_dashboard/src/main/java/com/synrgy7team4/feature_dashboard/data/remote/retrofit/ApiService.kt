package com.synrgy7team4.feature_dashboard.data.remote.retrofit

import com.synrgy7team4.feature_dashboard.data.Post
import com.synrgy7team4.feature_dashboard.data.remote.response.BalanceResponse
import com.synrgy7team4.feature_dashboard.data.remote.response.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
//
//    @GET("posts")
//    suspend fun getPosts(): Response<List<Post>>

    @GET("user/me")
    suspend fun getUserData(
        @Header("Authorization") token: String,
    ) : Response<UserResponse>

    @GET("balance/get")
    suspend fun getBalance(
        @Header("Authorization") token: String,
        @Query("accountNumber") accountNumber: String
    ): BalanceResponse
}