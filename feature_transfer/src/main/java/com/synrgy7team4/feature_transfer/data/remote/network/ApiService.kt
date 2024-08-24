package com.synrgy7team4.feature_transfer.data.remote.network

import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.data.remote.request.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.TransSchedule
import com.synrgy7team4.feature_transfer.data.remote.request.TransferRequest
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountResponse
import com.synrgy7team4.feature_transfer.data.remote.response.account.Accounts
import com.synrgy7team4.feature_transfer.data.remote.response.balance.BalanceResponse
import com.synrgy7team4.feature_transfer.data.remote.response.mutations.MutationsResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transschedule.TransSchedData
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //transaction/transfer
    @POST("transaction/transfer")
    suspend fun postTransfer(
        @Header("Authorization") token:String,
        @Body transferRequest: TransferRequest
    ) : TransferResponse

    //transaction/schedule
    @POST("transaction/schedule")
    suspend fun postTransferSched(
        @Header("Authorization") token: String,
        @Body transferSched: TransSchedule
    ) : TransSchedData

    //get user
    @GET("user/me")
    suspend fun getUserData(
        @Header("Authorization") token:String,
    ): UserResponse


    // Balance (get+post)
    @GET("balance/get")
    suspend fun getBalance(
        @Header("Authorization") token:String,
        @Query("accountNumber") accountNumber: String
    ): BalanceResponse

    @POST("balance/set")
    suspend fun postBalance(
        @Header("Authorization") token:String,
        @Body balanceRequest: BalanceRequest
    ) : BalanceResponse

    //mutations
    @GET ("transaction/mutation")
    suspend fun getMutation(
        @Header("Authorization") token:String,
        @Query ("id")id: String
    ) : MutationsResponse


    // Account list(get+post)
    @GET("account-list")
    suspend fun getAccountList(
        @Header("Authorization") token:String
    ): AccountResponse

    @POST("account-list/save")
    suspend fun postAccount(
        @Header("Authorization") token:String,
        @Body request: AccountRequest
    ) :AccountResponse

    @GET("accounts")
    suspend fun cekAccount(
        @Header("Authorization") token:String,
    ) : List<Accounts>


}