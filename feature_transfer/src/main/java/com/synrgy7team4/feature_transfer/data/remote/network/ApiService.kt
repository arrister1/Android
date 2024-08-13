package com.synrgy7team4.feature_transfer.data.remote.network

import com.synrgy7team4.feature_transfer.data.remote.request.balance.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.transfer.TransferRequest
import com.synrgy7team4.feature_transfer.data.remote.response.balance.BalanceResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferResponse
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //transfer
    @POST("transaction/transfer")
    suspend fun postTransfer(
        @Body transferRequest: TransferRequest
    ) : TransferResponse


    //get user
    @GET("user/me")
    suspend fun getUserData(): Response<UserResponse>

    // get balance
    @GET("balance/get")
    suspend fun getBalance(): Response<BalanceResponse>

    //post balance
    @POST("balance/set")
    suspend fun postBalance(
        @Body balanceRequest: BalanceRequest
    ) : BalanceResponse
}