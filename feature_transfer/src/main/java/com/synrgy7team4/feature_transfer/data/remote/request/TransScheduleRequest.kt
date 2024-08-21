package com.synrgy7team4.feature_transfer.data.remote.request


import com.google.gson.annotations.SerializedName

data class TransSchedule(
    @SerializedName("accountFrom")
    val accountFrom: String,
    @SerializedName("accountTo")
    val accountTo: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("pin")
    val pin: String
)