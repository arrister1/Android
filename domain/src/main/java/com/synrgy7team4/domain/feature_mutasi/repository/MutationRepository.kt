package com.synrgy7team4.domain.feature_mutasi.repository

import com.synrgy7team4.domain.feature_mutasi.model.response.MutationGetResponseDomain

interface MutationRepository {
    suspend fun getAllMutations(jwtToken: String, accountNumber: String): MutationGetResponseDomain
    suspend fun getMutationsByDate(jwtToken: String, accountNumber: String, startDate: String, endDate: String, type: String): MutationGetResponseDomain
}