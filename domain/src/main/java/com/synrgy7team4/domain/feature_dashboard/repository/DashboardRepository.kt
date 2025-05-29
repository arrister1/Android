package com.synrgy7team4.domain.feature_dashboard.repository

import com.synrgy7team4.domain.feature_dashboard.model.response.BalanceGetResponseDomain
import com.synrgy7team4.domain.feature_dashboard.model.response.NotificationGetResponseItemDomain

interface DashboardRepository {
    suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponseDomain
    suspend fun getNotification(jwtToken: String): List<NotificationGetResponseItemDomain>
}