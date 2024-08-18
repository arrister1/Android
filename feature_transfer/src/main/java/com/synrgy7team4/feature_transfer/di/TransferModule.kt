package com.synrgy7team4.feature_transfer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.synrgy7team4.feature_transfer.data.remote.RemoteDataSource
import com.synrgy7team4.feature_transfer.data.remote.network.provideConverterFactory
import com.synrgy7team4.feature_transfer.data.remote.network.provideHttpClient
import com.synrgy7team4.feature_transfer.data.remote.network.provideHttpLoggingInterceptor
import com.synrgy7team4.feature_transfer.data.remote.network.provideRetrofit
import com.synrgy7team4.feature_transfer.data.remote.network.provideService
import com.synrgy7team4.feature_transfer.data.remote.repository.TransferRepositoryImpl
import com.synrgy7team4.feature_transfer.domain.repository.TransferRepository
import com.synrgy7team4.feature_transfer.domain.usecase.TransferUseCase
import com.synrgy7team4.feature_transfer.presentation.viewmodel.TransferViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val TransferModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get()) }
    single { provideHttpLoggingInterceptor() }
    single { provideService() }

    single { RemoteDataSource(get()) }

    single<TransferRepository> { TransferRepositoryImpl(get()) }

    single { TransferUseCase(get()) }

    viewModel { TransferViewModel(get()) }

}