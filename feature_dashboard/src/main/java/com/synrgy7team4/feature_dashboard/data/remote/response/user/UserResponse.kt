package com.synrgy7team4.feature_dashboard.data.remote.response.user


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)