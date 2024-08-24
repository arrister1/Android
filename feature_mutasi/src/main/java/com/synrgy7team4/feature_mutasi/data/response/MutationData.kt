package com.synrgy7team4.feature_mutasi.data.response

import com.google.gson.annotations.SerializedName

data class MutationData(
    @SerializedName("id")
    val id: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("account_from")
    val account_from: String,
    @SerializedName("account_to")
    val account_to: String,
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("username_from")
    val usernameFrom: String,
    @SerializedName("username_to")
    val username_to: String
)

