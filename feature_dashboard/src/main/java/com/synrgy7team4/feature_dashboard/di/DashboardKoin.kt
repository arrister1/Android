package com.synrgy7team4.feature_dashboard.di

import com.synrgy7team4.feature_dashboard.data.Repository
import com.synrgy7team4.feature_dashboard.data.remote.RemoteDataSource
import com.synrgy7team4.feature_dashboard.data.remote.provideConverterFactory
import com.synrgy7team4.feature_dashboard.data.remote.provideHttpClient
import com.synrgy7team4.feature_dashboard.data.remote.provideRetrofit
import com.synrgy7team4.feature_dashboard.data.remote.provideService
import org.koin.dsl.module

val MutasiKoin = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(),get()) }
    single { provideService(get()) }

    factory {  RemoteDataSource(get()) }
    factory {  Repository(get()) }
}