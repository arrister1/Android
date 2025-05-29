package com.synrgy7team4.data.feature_mutasi.repository

import com.synrgy7team4.data.feature_mutasi.FileUtils.toMutationGetResponseDomain
import com.synrgy7team4.data.feature_mutasi.datasource.remote.MutationRemoteDatasource
import com.synrgy7team4.domain.feature_mutasi.model.response.MutationGetResponseDomain
import com.synrgy7team4.domain.feature_mutasi.repository.MutationRepository

class ImplementMutationRepository(
    private val mutationRemoteDatasource: MutationRemoteDatasource
) : MutationRepository {
    override suspend fun getAllMutations(jwtToken: String, accountNumber: String): MutationGetResponseDomain =
        mutationRemoteDatasource.getAllMutations(jwtToken, accountNumber).toMutationGetResponseDomain()

    override suspend fun getMutationsByDate(
        jwtToken: String,
        accountNumber: String,
        startDate: String,
        endDate: String,
        type: String
    ): MutationGetResponseDomain =
        mutationRemoteDatasource.getMutationsByDate(jwtToken, accountNumber, startDate, endDate, type).toMutationGetResponseDomain()
}