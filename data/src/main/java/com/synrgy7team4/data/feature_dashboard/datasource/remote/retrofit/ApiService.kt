package com.synrgy7team4.data.feature_dashboard.datasource.remote.retrofit

import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.BalanceGetResponse
import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.NotificationGetResponseItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("balance/get")
    suspend fun getBalance(
        @Header("Authorization") jwtToken: String,
        @Query("accountNumber") accountNumber: String
    ): BalanceGetResponse

    @GET("notification/")
    suspend fun getNotification(
        @Header("Authorization") jwtToken: String
    ): List<NotificationGetResponseItem>
}