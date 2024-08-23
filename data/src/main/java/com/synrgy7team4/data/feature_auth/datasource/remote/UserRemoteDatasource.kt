package com.synrgy7team4.data.feature_auth.datasource.remote

import com.synrgy7team4.data.feature_auth.datasource.remote.response.UserGetResponse

interface UserRemoteDatasource {
    suspend fun getUserData(jwtToken: String): UserGetResponse
}