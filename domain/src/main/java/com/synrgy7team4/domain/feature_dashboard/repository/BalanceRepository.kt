package com.synrgy7team4.domain.feature_dashboard.repository

import com.synrgy7team4.domain.feature_dashboard.model.response.BalanceGetResponseDomain

interface BalanceRepository {
    suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponseDomain
}