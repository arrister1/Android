package com.synrgy7team4.feature_transfer.data.remote.request.transfer


import com.google.gson.annotations.SerializedName

data class TransferRequest(
    @SerializedName("accountFrom")
    val accountFrom: String,
    @SerializedName("accountTo")
    val accountTo: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("pin")
    val pin: String
)