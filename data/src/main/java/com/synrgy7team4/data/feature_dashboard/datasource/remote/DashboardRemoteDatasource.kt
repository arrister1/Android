package com.synrgy7team4.data.feature_dashboard.datasource.remote

import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.BalanceGetResponse
import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.NotificationGetResponseItem

interface DashboardRemoteDatasource {
    suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponse
    suspend fun getNotification(jwtToken: String): List<NotificationGetResponseItem>
}