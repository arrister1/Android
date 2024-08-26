package com.synrgy7team4.di.feature_mutasi

import com.synrgy7team4.data.feature_mutasi.datasource.remote.ImplementMutationRemoteDatasource
import com.synrgy7team4.data.feature_mutasi.datasource.remote.MutationRemoteDatasource
import com.synrgy7team4.data.feature_mutasi.datasource.remote.retrofit.ApiConfig
import com.synrgy7team4.data.feature_mutasi.datasource.remote.retrofit.ApiService
import com.synrgy7team4.data.feature_mutasi.repository.ImplementMutationRepository
import com.synrgy7team4.domain.feature_mutasi.repository.MutationRepository
import com.synrgy7team4.domain.feature_mutasi.usecase.MutasiUseCase
import org.koin.dsl.module

val mutasiKoin = module {
    single<MutasiUseCase> { MutasiUseCase(get()) }

    single<MutationRepository> { ImplementMutationRepository(get()) }
    single<MutationRemoteDatasource> { ImplementMutationRemoteDatasource(get()) }

    single<ApiService> { ApiConfig.provideApiService(get()) }
}