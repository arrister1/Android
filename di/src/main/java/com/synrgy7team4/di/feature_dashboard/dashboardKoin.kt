package com.synrgy7team4.di.feature_dashboard

import com.synrgy7team4.data.feature_dashboard.datasource.remote.BalanceRemoteDatasource
import com.synrgy7team4.data.feature_dashboard.datasource.remote.ImplementBalanceRemoteDatasource
import com.synrgy7team4.data.feature_dashboard.datasource.remote.retrofit.ApiConfig
import com.synrgy7team4.data.feature_dashboard.datasource.remote.retrofit.ApiService
import com.synrgy7team4.data.feature_dashboard.repository.ImplementBalanceRepository
import com.synrgy7team4.domain.feature_dashboard.repository.BalanceRepository
import com.synrgy7team4.domain.feature_dashboard.usecase.BalanceUseCase
import org.koin.dsl.module

val dashboardKoin = module {
    single<BalanceUseCase> { BalanceUseCase(get())}

    single<BalanceRepository> { ImplementBalanceRepository(get()) }
    single<BalanceRemoteDatasource> { ImplementBalanceRemoteDatasource(get()) }

    single<ApiService> { ApiConfig.provideApiService(get()) }
}