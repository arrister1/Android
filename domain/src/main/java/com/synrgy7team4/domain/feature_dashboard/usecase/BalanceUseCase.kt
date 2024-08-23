package com.synrgy7team4.domain.feature_dashboard.usecase

import com.synrgy7team4.domain.feature_dashboard.repository.BalanceRepository

class BalanceUseCase(
    private val repository: BalanceRepository
) {
    suspend fun getBalance(jwtToken: String, accountNumber: String) =
        repository.getBalance(jwtToken, accountNumber)
}