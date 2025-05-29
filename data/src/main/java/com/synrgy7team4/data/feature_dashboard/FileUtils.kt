package com.synrgy7team4.data.feature_dashboard

import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.BalanceGetResponse
import com.synrgy7team4.data.feature_dashboard.datasource.remote.response.NotificationGetResponseItem
import com.synrgy7team4.domain.feature_dashboard.model.response.BalanceGetResponseDomain
import com.synrgy7team4.domain.feature_dashboard.model.response.NotificationGetResponseItemDomain

object FileUtils {
    fun BalanceGetResponse.toDomain(): BalanceGetResponseDomain =
        BalanceGetResponseDomain(
            success = success,
            message = message,
            data = data
        )

    fun NotificationGetResponseItem.toDomain(): NotificationGetResponseItemDomain =
        NotificationGetResponseItemDomain(
            id = id,
            title = title,
            body = body,
            sentAt = sentAt,
            read = read
        )
}