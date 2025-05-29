package com.synrgy7team4.data.feature_dashboard.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class BalanceGetResponse(
    @SerializedName("data")
    val data: Double,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)