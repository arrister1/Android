package com.synrgy7team4.feature_transfer.data.remote.request.balance


import com.google.gson.annotations.SerializedName

data class BalanceRequest(
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("newBalance")
    val newBalance: Int
)