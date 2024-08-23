package com.synrgy7team4.data.feature_dashboard.repository

import com.synrgy7team4.data.feature_dashboard.FileUtils.toBalanceGetResponseDomain
import com.synrgy7team4.data.feature_dashboard.datasource.remote.BalanceRemoteDatasource
import com.synrgy7team4.domain.feature_dashboard.model.response.BalanceGetResponseDomain
import com.synrgy7team4.domain.feature_dashboard.repository.BalanceRepository

class ImplementBalanceRepository(
    private val balanceRemoteSource: BalanceRemoteDatasource
) : BalanceRepository {
    override suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponseDomain =
        balanceRemoteSource.getBalance(jwtToken, accountNumber).toBalanceGetResponseDomain()
}