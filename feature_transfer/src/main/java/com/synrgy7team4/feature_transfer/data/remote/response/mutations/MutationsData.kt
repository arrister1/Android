package com.synrgy7team4.feature_transfer.data.remote.response.mutations


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("account_from")
    val accountFrom: String,
    @SerializedName("account_to")
    val accountTo: String,
    @SerializedName("amount")
    val amount: Double,  // Changed from Int to Double
    @SerializedName("balance")
    val balance: Double,  // Changed from Int to Double
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("username_from")
    val usernameFrom: String,
    @SerializedName("username_to")
    val usernameTo: String
)
