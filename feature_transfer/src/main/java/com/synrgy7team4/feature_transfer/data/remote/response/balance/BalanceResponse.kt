package com.synrgy7team4.feature_transfer.data.remote.response.balance


import com.google.gson.annotations.SerializedName

data class BalanceResponse(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("errors")
    val errors: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)