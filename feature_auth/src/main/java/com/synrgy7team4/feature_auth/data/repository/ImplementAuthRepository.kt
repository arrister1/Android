package com.synrgy7team4.feature_auth.data.repository

import com.synrgy7team4.feature_auth.data.local.AuthLocalSource
import com.synrgy7team4.feature_auth.data.remote.AuthRemoteSource
import com.synrgy7team4.feature_auth.data.remote.response.Data
import com.synrgy7team4.feature_auth.data.remote.response.DataX
import com.synrgy7team4.feature_auth.data.remote.response.LoginResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegisterResponse
import com.synrgy7team4.feature_auth.data.remote.retrofit.RegisterBody
import com.synrgy7team4.feature_auth.domain.model.UserModel
import com.synrgy7team4.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class ImplementAuthRepository(
    private val authLocalSource: AuthLocalSource,
    private val authRemoteSource: AuthRemoteSource
): AuthRepository {

//    override suspend fun register(name: String, email: String, password: String): RegisterResponse {
//        return authRemoteSource.register(name, email, password)
//    }

    override suspend fun register(registerBody: RegisterBody): Data {
        return authRemoteSource.register(registerBody)
    }

    override suspend fun login(email: String, password: String): DataX {
        return authRemoteSource.login(email, password)
    }

    override suspend fun saveSession(dataX: DataX) {
        authLocalSource.saveSession(dataX)
    }

    override suspend fun loadSession(): Flow<DataX> {
        return authLocalSource.loadSession()
    }

    override suspend fun deleteSession() {
        authLocalSource.deleteSession()
    }

//    override suspend fun saveToken(token: String) {
//        authLocalSource.saveToken(token)
//
//    }
//
//    override suspend fun loadtoken(): String? {
//        return authLocalSource.loadtoken()
//    }
//
//    override suspend fun deleteToken() {
//        authLocalSource.deleteToken()
//    }
}