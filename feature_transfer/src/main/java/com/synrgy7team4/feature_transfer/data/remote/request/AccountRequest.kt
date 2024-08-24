package com.synrgy7team4.feature_transfer.data.remote.request


import com.google.gson.annotations.SerializedName

data class AccountRequest(
    @SerializedName("accountNumber")
    val accountNumber: String,
)

fun AccountRequest.toDomain(): com.synrgy7team4.feature_transfer.domain.model.AccountReq{
    return com.synrgy7team4.feature_transfer.domain.model.AccountReq(
        accountNumber = accountNumber,
    )
}