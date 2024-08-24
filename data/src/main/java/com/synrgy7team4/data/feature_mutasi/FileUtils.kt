package com.synrgy7team4.data.feature_mutasi

import com.synrgy7team4.data.feature_mutasi.datasource.remote.response.MutationData
import com.synrgy7team4.data.feature_mutasi.datasource.remote.response.MutationGetResponse
import com.synrgy7team4.domain.feature_mutasi.model.response.MutationDataDomain
import com.synrgy7team4.domain.feature_mutasi.model.response.MutationGetResponseDomain

object FileUtils {
    fun MutationGetResponse.toMutationGetResponseDomain(): MutationGetResponseDomain =
        MutationGetResponseDomain(
            data = data?.map { it.toMutationDataDomain() } ?: emptyList(),
            success = success,
            message = message
        )

    private fun MutationData.toMutationDataDomain(): MutationDataDomain =
        MutationDataDomain(
            usernameFrom = usernameFrom ?: "",
            amount = amount ?: 0.0,
            datetime = datetime  ?: "",
            accountTo = accountTo  ?: "",
            balance = balance  ?: 0.0,
            usernameTo = usernameTo  ?: "",
            description = description  ?: "",
            id = id  ?: "",
            accountFrom = accountFrom ?: "",
            type = type ?: "",
            status = status ?: ""
        )
}