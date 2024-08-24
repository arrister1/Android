package com.synrgy7team4.data.feature_mutasi.datasource.remote

import com.synrgy7team4.data.feature_mutasi.datasource.remote.response.MutationGetResponse

interface MutationRemoteDatasource {
    suspend fun getAllMutations(jwtToken: String, accountNumber: String): MutationGetResponse
    suspend fun getMutationsByDate(jwtToken: String, accountNumber: String, startDate: String, endDate: String, type: String): MutationGetResponse
}