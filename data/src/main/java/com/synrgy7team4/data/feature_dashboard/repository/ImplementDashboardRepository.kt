package com.synrgy7team4.data.feature_dashboard.repository

import com.synrgy7team4.data.feature_dashboard.FileUtils.toDomain
import com.synrgy7team4.data.feature_dashboard.datasource.remote.DashboardRemoteDatasource
import com.synrgy7team4.domain.feature_dashboard.model.response.BalanceGetResponseDomain
import com.synrgy7team4.domain.feature_dashboard.model.response.NotificationGetResponseItemDomain
import com.synrgy7team4.domain.feature_dashboard.repository.DashboardRepository

class ImplementDashboardRepository(
    private val dashboardRemoteSource: DashboardRemoteDatasource
) : DashboardRepository {
    override suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponseDomain =
        dashboardRemoteSource.getBalance(jwtToken, accountNumber).toDomain()

    override suspend fun getNotification(jwtToken: String): List<NotificationGetResponseItemDomain> =
        dashboardRemoteSource.getNotification(jwtToken).map { it.toDomain() }
}