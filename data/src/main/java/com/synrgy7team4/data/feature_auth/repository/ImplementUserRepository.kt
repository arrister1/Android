package com.synrgy7team4.data.feature_auth.repository

import com.synrgy7team4.data.feature_auth.FileUtils.toUserGetResponseDomain
import com.synrgy7team4.data.feature_auth.datasource.remote.UserRemoteDatasource
import com.synrgy7team4.domain.feature_auth.model.response.UserGetResponseDomain
import com.synrgy7team4.domain.feature_auth.repository.UserRepository

class ImplementUserRepository(
    private val userRemoteSource: UserRemoteDatasource
): UserRepository {
    override suspend fun getUserData(jwtToken: String): UserGetResponseDomain =
        userRemoteSource.getUserData(jwtToken).toUserGetResponseDomain()
}