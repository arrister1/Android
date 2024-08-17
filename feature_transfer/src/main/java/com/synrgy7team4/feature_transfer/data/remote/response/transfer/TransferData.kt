package com.synrgy7team4.feature_transfer.data.remote.response.transfer


import com.google.gson.annotations.SerializedName
import com.synrgy7team4.feature_transfer.domain.model.Transfer

data class TransferData(
    @SerializedName("accountFrom")
    val accountFrom: String,
    @SerializedName("accountTo")
    val accountTo: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("balance")
    val balance: Int,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("nameAccountFrom")
    val nameAccountFrom: String,
    @SerializedName("nameAccountTo")
    val nameAccountTo: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String
)

fun TransferData.toDomain(): Transfer{
    return Transfer(
        accountFrom  = accountFrom,
        accountTo = accountTo,
        amount = amount,
        balance = balance,
        datetime = datetime,
        description = description,
        id = id,
        nameAccountFrom = nameAccountFrom,
        nameAccountTo = nameAccountTo,
        status = status,
        type = type
    )
}