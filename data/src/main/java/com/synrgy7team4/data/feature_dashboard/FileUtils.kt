package com.synrgy7team4.data.feature_dashboard

import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.BalanceGetResponse
import com.synrgy7team4.domain.feature_dashboard.model.response.BalanceGetResponseDomain

object FileUtils {
    fun BalanceGetResponse.toBalanceGetResponseDomain(): BalanceGetResponseDomain =
        BalanceGetResponseDomain(
            success = success,
            message = message,
            data = data
        )
}