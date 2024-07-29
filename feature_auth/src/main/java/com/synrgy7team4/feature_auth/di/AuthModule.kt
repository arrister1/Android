package com.synrgy7team4.feature_auth.di

import com.synrgy7team4.feature_auth.data.Repository
import com.synrgy7team4.feature_auth.data.remote.retrofit.ApiConfig
import com.synrgy7team4.feature_auth.data.remote.retrofit.ApiService
import com.synrgy7team4.feature_auth.data.remote.RemoteDataSource
import org.koin.dsl.module

val AuthKoin = module {
    single<ApiService> { ApiConfig.provideApiService(get()) }

    factory {  RemoteDataSource(get()) }
    factory {  Repository(get()) }
}