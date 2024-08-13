package com.synrgy7team4.feature_transfer.data.remote.response.transfer


import com.google.gson.annotations.SerializedName

data class TransferResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)