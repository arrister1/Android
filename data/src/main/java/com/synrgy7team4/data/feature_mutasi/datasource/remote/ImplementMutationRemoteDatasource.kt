package com.synrgy7team4.data.feature_mutasi.datasource.remote

import com.synrgy7team4.data.feature_mutasi.datasource.remote.response.MutationGetResponse
import com.synrgy7team4.data.feature_mutasi.datasource.remote.retrofit.ApiService
import com.synrgy7team4.domain.feature_transfer.usecase.HttpExceptionUseCase
import retrofit2.HttpException

class ImplementMutationRemoteDatasource(
    private val apiService: ApiService
) : MutationRemoteDatasource {
    override suspend fun getAllMutations(jwtToken: String, accountNumber: String): MutationGetResponse =
        try {
            apiService.getAllMutations(jwtToken, accountNumber)
        } catch (e: HttpException) {
            val json = e.response()?.code()
            if (json == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }

    override suspend fun getMutationsByDate(
        jwtToken: String,
        accountNumber: String,
        startDate: String,
        endDate: String,
        type: String
    ): MutationGetResponse =
        try {
            apiService.getMutationsByDate(jwtToken, accountNumber, startDate, endDate, type)
        } catch (e: HttpException) {
            val json = e.response()?.code()
            if (json == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }
}