package com.synrgy7team4.feature_auth_data

import com.synrgy7team4.feature_auth_data.remote.RemoteDataSource

class Repository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getPostList() = remoteDataSource.getPosts()
}