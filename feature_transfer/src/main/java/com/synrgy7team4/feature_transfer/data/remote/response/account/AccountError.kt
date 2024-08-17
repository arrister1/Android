package com.synrgy7team4.feature_transfer.data.remote.response.account


import com.google.gson.annotations.SerializedName

data class AccountError(
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)