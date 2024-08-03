package com.synrgy7team4.feature_mutasi.presentation.viewmodel

import com.synrgy7team4.feature_mutasi.data.Repository
import com.synrgy7team4.feature_mutasi.data.remote.ApiConfig
import com.synrgy7team4.feature_mutasi.data.remote.ApiService
import com.synrgy7team4.feature_mutasi.data.remote.RemoteDataSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MutasiViewModelKoin = module {
    single<ApiService> { ApiConfig.provideApiService(get()) }
    single { RemoteDataSource(get()) }

    // Provide the MutationRepository instance
    single { Repository(get()) }

    // Provide the MutationViewModel instance
    viewModel { MutasiViewmodel(get()) }
}


