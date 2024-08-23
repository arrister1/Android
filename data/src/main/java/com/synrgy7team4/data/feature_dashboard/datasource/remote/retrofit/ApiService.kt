package com.synrgy7team4.data.feature_dashboard.datasource.remote.retrofit

import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.BalanceGetResponse
import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.BalanceSetResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.UserGetResponse
import com.synrgy7team4.domain.feature_dashboard.model.request.BalanceSetRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("balance/get")
    suspend fun getBalance(
        @Header("Authorization") jwtToken: String,
        @Query("accountNumber") accountNumber: String
    ): BalanceGetResponse

    @POST("balance/set")
    suspend fun setBalance(
        @Header("Authorization") jwtToken: String,
        @Body balanceSetRequest: BalanceSetRequest
    ): BalanceSetResponse
}