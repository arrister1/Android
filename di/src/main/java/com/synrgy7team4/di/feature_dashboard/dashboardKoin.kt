package com.synrgy7team4.di.feature_dashboard

import com.synrgy7team4.data.feature_dashboard.datasource.remote.DashboardRemoteDatasource
import com.synrgy7team4.data.feature_dashboard.datasource.remote.ImplementDashboardRemoteDatasource
import com.synrgy7team4.data.feature_dashboard.datasource.remote.retrofit.ApiConfig
import com.synrgy7team4.data.feature_dashboard.datasource.remote.retrofit.ApiService
import com.synrgy7team4.data.feature_dashboard.repository.ImplementDashboardRepository
import com.synrgy7team4.domain.feature_dashboard.repository.DashboardRepository
import com.synrgy7team4.domain.feature_dashboard.usecase.DashboardUseCase
import org.koin.dsl.module

val dashboardKoin = module {
    single<DashboardUseCase> { DashboardUseCase(get())}

    single<DashboardRepository> { ImplementDashboardRepository(get()) }
    single<DashboardRemoteDatasource> { ImplementDashboardRemoteDatasource(get()) }

    single<ApiService> { ApiConfig.provideApiService(get()) }
}