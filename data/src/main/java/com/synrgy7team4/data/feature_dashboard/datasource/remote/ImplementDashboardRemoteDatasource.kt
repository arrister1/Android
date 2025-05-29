package com.synrgy7team4.data.feature_dashboard.datasource.remote

import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.BalanceGetResponse
import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.NotificationGetResponseItem
import com.synrgy7team4.data.feature_dashboard.datasource.remote.retrofit.ApiService
import com.synrgy7team4.domain.feature_dashboard.usecase.HttpExceptionUseCase
import retrofit2.HttpException

class ImplementDashboardRemoteDatasource(
    private val apiService: ApiService
): DashboardRemoteDatasource {
    override suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponse =
        try {
            apiService.getBalance(jwtToken, accountNumber)
        } catch (e: HttpException) {
            val json = e.response()?.code()
            if (json == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }

    override suspend fun getNotification(jwtToken: String): List<NotificationGetResponseItem> =
        try {
            apiService.getNotification(jwtToken)
        } catch (e: HttpException) {
            val json = e.response()?.code()
            if (json == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }
}