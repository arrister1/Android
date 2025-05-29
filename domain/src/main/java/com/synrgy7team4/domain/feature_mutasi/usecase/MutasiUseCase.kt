package com.synrgy7team4.domain.feature_mutasi.usecase

import com.synrgy7team4.domain.feature_mutasi.model.response.MutationGetResponseDomain
import com.synrgy7team4.domain.feature_mutasi.repository.MutationRepository

class MutasiUseCase(
    private val mutationRepository: MutationRepository
) {
    suspend fun getAllMutations(jwtToken: String, accountNumber: String): MutationGetResponseDomain =
        mutationRepository.getAllMutations(jwtToken, accountNumber)

    suspend fun getMutationsByDate(
        jwtToken: String,
        accountNumber: String,
        startDate: String,
        endDate: String,
        type: String
    ): MutationGetResponseDomain =
        mutationRepository.getMutationsByDate(jwtToken, accountNumber, startDate, endDate, type)
}