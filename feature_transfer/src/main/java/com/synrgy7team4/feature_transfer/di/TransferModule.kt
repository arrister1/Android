package com.synrgy7team4.feature_transfer.di

import com.synrgy7team4.common.SharedPrefHelper
import com.synrgy7team4.feature_transfer.data.remote.RemoteDataSource
import com.synrgy7team4.feature_transfer.data.remote.network.ApiConfig
import com.synrgy7team4.feature_transfer.data.remote.network.ApiService
import com.synrgy7team4.feature_transfer.data.remote.repository.TransferRepositoryImpl
import com.synrgy7team4.feature_transfer.domain.repository.TransferRepository
import com.synrgy7team4.feature_transfer.domain.usecase.TransferUseCase
import com.synrgy7team4.feature_transfer.presentation.viewmodel.TransferViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val TransferModule = module {
    single { SharedPrefHelper(get()) }

    // Provide ApiService instance
    single<ApiService> {
        val sharedPrefHelper = get<SharedPrefHelper>()
        val token = sharedPrefHelper.getJwtToken() ?: ""
        ApiConfig.provideApiService(get(), token)
    }

    single { RemoteDataSource(get()) }

    single<TransferRepository> { TransferRepositoryImpl(get()) }

    single { TransferUseCase(get()) }


    viewModel { TransferViewModel(get()) }
}