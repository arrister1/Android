package com.synrgy7team4.di.feature_auth

import com.synrgy7team4.data.feature_auth.datasource.remote.AuthRemoteDatasource
import com.synrgy7team4.data.feature_auth.datasource.remote.ImplementAuthRemoteDatasource
import com.synrgy7team4.data.feature_auth.datasource.remote.ImplementUserRemoteDatasource
import com.synrgy7team4.data.feature_auth.datasource.remote.UserRemoteDatasource
import com.synrgy7team4.data.feature_auth.datasource.remote.retrofit.ApiConfig
import com.synrgy7team4.data.feature_auth.datasource.remote.retrofit.ApiService
import com.synrgy7team4.data.feature_auth.repository.ImplementAuthRepository
import com.synrgy7team4.data.feature_auth.repository.ImplementUserRepository
import com.synrgy7team4.domain.feature_auth.repository.AuthRepository
import com.synrgy7team4.domain.feature_auth.repository.UserRepository
import com.synrgy7team4.domain.feature_auth.usecase.LoginUseCase
import com.synrgy7team4.domain.feature_auth.usecase.RegisterUseCase
import com.synrgy7team4.domain.feature_auth.usecase.UserUseCase
import org.koin.dsl.module

val authKoin = module {
    single<LoginUseCase> { LoginUseCase(get()) }
    single<RegisterUseCase> { RegisterUseCase(get()) }
    single<UserUseCase> { UserUseCase(get()) }

    single<AuthRemoteDatasource> { ImplementAuthRemoteDatasource(get()) }
    single<AuthRepository> { ImplementAuthRepository(get()) }
    single<UserRemoteDatasource> { ImplementUserRemoteDatasource(get()) }
    single<UserRepository> { ImplementUserRepository(get()) }

    single<ApiService> { ApiConfig.provideApiService(get()) }
}