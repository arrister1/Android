package com.synrgy7team4.feature_transfer.data.remote.response.user


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val `data`: UserData,
    @SerializedName("errors")
    val errors: UserErrors,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)