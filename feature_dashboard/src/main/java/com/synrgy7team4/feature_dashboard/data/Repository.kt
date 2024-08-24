package com.synrgy7team4.feature_dashboard.data

import com.synrgy7team4.feature_dashboard.data.remote.RemoteDataSource

class Repository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getUserData(token: String) = remoteDataSource.getUserData(token)
    suspend fun getBalance(token: String, accountNumber: String) = remoteDataSource.getBalance(token, accountNumber)



}