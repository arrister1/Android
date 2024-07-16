package com.synrgy7team4.feature_auth.di

import com.synrgy7team4.feature_auth.data.Repository
import com.synrgy7team4.feature_auth.data.remote.RemoteDataSource
import com.synrgy7team4.feature_auth.data.remote.provideConverterFactory
import com.synrgy7team4.feature_auth.data.remote.provideHttpClient
import com.synrgy7team4.feature_auth.data.remote.provideRetrofit
import com.synrgy7team4.feature_auth.data.remote.provideService
import org.koin.dsl.module

val AuthKoin = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(),get()) }
    single { provideService(get()) }

    factory {  RemoteDataSource(get()) }
    factory {  Repository(get()) }
}