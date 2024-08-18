package com.synrgy7team4.di.feature_auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.synrgy7team4.data.feature_auth.datasource.local.AuthLocalDatasource
import com.synrgy7team4.data.feature_auth.datasource.local.ImplementLocalDatasource
import com.synrgy7team4.data.feature_auth.datasource.local.authDataStore
import com.synrgy7team4.data.feature_auth.datasource.remote.AuthRemoteDatasource
import com.synrgy7team4.data.feature_auth.datasource.remote.ImplementAuthRemoteDatasource
import com.synrgy7team4.data.feature_auth.datasource.remote.retrofit.ApiConfig
import com.synrgy7team4.data.feature_auth.datasource.remote.retrofit.ApiService
import com.synrgy7team4.data.feature_auth.repository.ImplementAuthRepository
import com.synrgy7team4.domain.feature_auth.repository.AuthRepository
import com.synrgy7team4.domain.feature_auth.usecase.LoginUseCase
import com.synrgy7team4.domain.feature_auth.usecase.RegisterUseCase
import com.synrgy7team4.domain.feature_auth.usecase.TokenUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authKoin = module {
    single<LoginUseCase> { LoginUseCase(get()) }
    single<RegisterUseCase> { RegisterUseCase(get()) }
    single<TokenUseCase> { TokenUseCase(get()) }

    single<AuthLocalDatasource> { ImplementLocalDatasource(get()) }
    single<AuthRemoteDatasource> { ImplementAuthRemoteDatasource(get()) }
    single<AuthRepository> { ImplementAuthRepository(get(), get()) }

    single<ApiService> { ApiConfig.provideApiService(get()) }

    single<DataStore<Preferences>> { androidContext().authDataStore }
}