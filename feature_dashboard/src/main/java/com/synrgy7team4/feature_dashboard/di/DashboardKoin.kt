package com.synrgy7team4.feature_dashboard.di

import com.synrgy7team4.feature_dashboard.data.Repository
import com.synrgy7team4.feature_dashboard.data.remote.RemoteDataSource
import com.synrgy7team4.feature_dashboard.data.remote.retrofit.provideConverterFactory
import com.synrgy7team4.feature_dashboard.data.remote.retrofit.provideService
import com.synrgy7team4.feature_dashboard.presentation.ui.home.HomeViewModel
import com.synrgy7team4.feature_mutasi.presentation.viewmodel.MutasiViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DashboardKoin = module {
    single { provideConverterFactory() }
    single { provideService() }

    factory {  RemoteDataSource(get()) }
    factory {  Repository(get()) }

    viewModel { HomeViewModel(get()) }
}