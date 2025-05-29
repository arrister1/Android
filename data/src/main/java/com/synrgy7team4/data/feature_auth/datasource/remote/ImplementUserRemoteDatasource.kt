package com.synrgy7team4.data.feature_auth.datasource.remote

import com.synrgy7team4.data.feature_auth.datasource.remote.response.UserGetResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.retrofit.ApiService
import com.synrgy7team4.domain.feature_auth.usecase.HttpExceptionUseCase
import retrofit2.HttpException

class ImplementUserRemoteDatasource(
    private val apiService: ApiService
): UserRemoteDatasource {
    override suspend fun getUserData(jwtToken: String): UserGetResponse =
        try {
            apiService.getUser(jwtToken)
        } catch (e: HttpException) {
            val json = e.response()?.code()
            if (json == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }
}