package com.synrgy7team4.feature_transfer.data.remote.response.mutations


import com.google.gson.annotations.SerializedName

data class MutationsRespomse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("errors")
    val errors: List<Error>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)