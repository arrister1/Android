package com.synrgy7team4.di.feature_transfer

import com.synrgy7team4.data.feature_transfer.datasource.remote.RemoteDatasource
import com.synrgy7team4.data.feature_transfer.datasource.remote.retrofit.ApiConfig
import com.synrgy7team4.data.feature_transfer.datasource.remote.retrofit.ApiService
import com.synrgy7team4.data.feature_transfer.repository.TransferRepositoryImpl
import com.synrgy7team4.domain.feature_transfer.repository.TransferRepository
import com.synrgy7team4.domain.feature_transfer.usecase.TransferUseCase
import org.koin.dsl.module

val transferKoin = module {
    single<TransferUseCase> { TransferUseCase(get()) }

    single<TransferRepository> { TransferRepositoryImpl(get()) }
    single<RemoteDatasource> { RemoteDatasource(get()) }

    single<ApiService> { ApiConfig.provideApiService(get()) }
}