package com.synrgy7team4.feature_transfer.data.remote.network

import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.data.remote.request.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.TransferRequest
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountResponse
import com.synrgy7team4.feature_transfer.data.remote.response.balance.BalanceResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferResponse
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //transfer
    @POST("transaction/transfer")
    suspend fun postTransfer(
        @Body transferRequest: TransferRequest
    ) : TransferResponse

    //get user
    @GET("user/me")
    suspend fun getUserData(): UserResponse

    // Balance (get+post)
    @GET("balance/get")
    suspend fun getBalance(
        @Query("accountNumber") accountNumber: String
    ): BalanceResponse

    @POST("balance/set")
    suspend fun postBalance(
        @Body balanceRequest: BalanceRequest
    ) : BalanceResponse

    // Account list(get+post)
    @GET("account-list")
    suspend fun getAccountList(): AccountResponse

    @POST("account-list/save")
    suspend fun postAccount(@Body request: AccountRequest) :AccountResponse

}