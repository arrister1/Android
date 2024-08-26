package com.synrgy7team4.data.feature_transfer.datasource.remote.retrofit

import com.synrgy7team4.data.feature_transfer.datasource.remote.response.MutationGetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.BalanceGetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.BalanceSetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.AccountSaveResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.SavedAccountsGetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.TransferResponse
import com.synrgy7team4.domain.feature_transfer.model.request.BalanceSetRequest
import com.synrgy7team4.domain.feature_transfer.model.request.AccountSaveRequest
import com.synrgy7team4.domain.feature_transfer.model.request.TransferRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("transaction/transfer")
    suspend fun transfer(
        @Header("Authorization") jwtToken: String,
        @Body transferRequest: TransferRequest
    ): TransferResponse

    @POST("balance/set")
    suspend fun setBalance(
        @Header("Authorization") jwtToken: String,
        @Body balanceSetRequest: BalanceSetRequest
    ): BalanceSetResponse

    @GET("balance/get")
    suspend fun getBalance(
        @Header("Authorization") jwtToken: String,
        @Query("accountNumber") accountNumber: String
    ): BalanceGetResponse

    @POST("account-list/save")
    suspend fun saveAccount(
        @Header("Authorization") jwtToken: String,
        @Body accountSaveRequest: AccountSaveRequest
    ): AccountSaveResponse

    @GET("account-list")
    suspend fun getSavedAccounts(
        @Header("Authorization") jwtToken: String
    ): SavedAccountsGetResponse

    @GET ("transaction/mutation")
    suspend fun getMutation(
        @Header("Authorization") token:String,
        @Query ("id") id: String
    ) : MutationGetResponse
}