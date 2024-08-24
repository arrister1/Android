package com.synrgy7team4.feature_transfer.data.remote.response.transfer


import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("success")
    val success: String,
    @SerializedName("errors")
    val errors: String,
)