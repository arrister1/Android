package com.synrgy7team4.domain.feature_dashboard.usecase

import com.synrgy7team4.domain.feature_dashboard.repository.DashboardRepository

class DashboardUseCase(
    private val dashboardRepository: DashboardRepository
) {
    suspend fun getBalance(jwtToken: String, accountNumber: String) =
        dashboardRepository.getBalance(jwtToken, accountNumber)

    suspend fun getNotification(jwtToken: String) =
        dashboardRepository.getNotification(jwtToken)
}