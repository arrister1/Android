package com.synrgy7team4.data.feature_auth.repository

import com.synrgy7team4.data.feature_auth.datasource.local.AuthLocalDatasource
import com.synrgy7team4.data.feature_auth.datasource.remote.AuthRemoteDatasource
import com.synrgy7team4.data.feature_auth.utils.FileUtils.toLoginResponseDomain
import com.synrgy7team4.data.feature_auth.utils.FileUtils.toRegisterResponseDomain
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.response.LoginResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain
import com.synrgy7team4.domain.feature_auth.repository.AuthRepository

class ImplementAuthRepository(
    private val authLocalDatasource: AuthLocalDatasource,
    private val authRemoteSource: AuthRemoteDatasource
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): RegisterResponseDomain {
        return authRemoteSource.register(registerRequest).toRegisterResponseDomain()
    }

    override suspend fun login(loginRequest: LoginRequest): LoginResponseDomain =
        authRemoteSource.login(loginRequest).toLoginResponseDomain()

    override suspend fun saveTokens(jwtToken: String, refreshToken: String) =
        authLocalDatasource.saveTokens(jwtToken, refreshToken)

    override suspend fun loadJwtToken(): String? =
        authLocalDatasource.loadJwtToken()

    override suspend fun deleteJwtToken() =
        authLocalDatasource.deleteJwtToken()
}