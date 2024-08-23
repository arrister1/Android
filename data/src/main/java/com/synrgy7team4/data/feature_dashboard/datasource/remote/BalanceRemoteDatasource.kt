package com.synrgy7team4.data.feature_dashboard.datasource.remote

import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.BalanceGetResponse

interface BalanceRemoteDatasource {
    suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponse
}