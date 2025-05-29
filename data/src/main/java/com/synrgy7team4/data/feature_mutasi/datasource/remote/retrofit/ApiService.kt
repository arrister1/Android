package com.synrgy7team4.data.feature_mutasi.datasource.remote.retrofit

import com.synrgy7team4.data.feature_mutasi.datasource.remote.response.MutationGetResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("transaction/mutations")
    suspend fun getAllMutations(
        @Header("Authorization") jwtToken: String,
        @Query("accountNumber") accountNumber: String
    ): MutationGetResponse

    @GET("transaction/mutation/date")
    suspend fun getMutationsByDate(
        @Header("Authorization") jwtToken: String,
        @Query("accountNumber") accountNumber: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("type") type: String,
    ): MutationGetResponse
}