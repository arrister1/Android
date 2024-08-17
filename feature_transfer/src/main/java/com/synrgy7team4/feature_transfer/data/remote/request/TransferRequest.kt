package com.synrgy7team4.feature_transfer.data.remote.request


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

fun TransferRequest.toDomain(): com.synrgy7team4.feature_transfer.domain.model.TransferRequest{
    return com.synrgy7team4.feature_transfer.domain.model.TransferRequest(
        accountFrom = accountFrom,
        accountTo = accountTo,
        amount = amount,
        description = description,
        pin = pin

    )
}