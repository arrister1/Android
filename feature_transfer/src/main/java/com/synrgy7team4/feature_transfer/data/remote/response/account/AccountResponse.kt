package com.synrgy7team4.feature_transfer.data.remote.response.account


import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("data")
    val `data`: List<AccountData>,
    @SerializedName("errors")
    val errors: List<AccountError>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)