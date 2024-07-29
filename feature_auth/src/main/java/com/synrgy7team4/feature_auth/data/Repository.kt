package com.synrgy7team4.feature_auth.data

import com.synrgy7team4.feature_auth.data.remote.RemoteDataSource

class Repository(private val remoteDataSource: RemoteDataSource) {
    suspend fun login(email: String, password: String) =
        remoteDataSource.login(email, password)
}