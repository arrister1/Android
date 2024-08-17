package com.synrgy7team4.feature_transfer.data.remote.request


import com.google.gson.annotations.SerializedName

data class BalanceRequest(
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("newBalance")
    val newBalance: Int
)

fun BalanceRequest.toDomain(): com.synrgy7team4.feature_transfer.domain.model.BalanceRequest{
    return com.synrgy7team4.feature_transfer.domain.model.BalanceRequest(
        accountNumber = accountNumber,
        newBalance = newBalance
    )
}