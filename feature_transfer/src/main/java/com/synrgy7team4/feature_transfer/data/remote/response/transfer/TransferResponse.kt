package com.synrgy7team4.feature_transfer.data.remote.response.transfer


import com.google.gson.annotations.SerializedName
import com.synrgy7team4.feature_transfer.domain.model.TransferRes

data class TransferResponse(
    @SerializedName("data")
    val data: TransferData?,
    @SerializedName("errors")
    val errors: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean
)

fun TransferResponse.toDomain(): TransferRes {
    return TransferRes(
        data = data?.toDomain(),
        errors = errors.toString(),
        message = message.toString(),
        success = success,
    )
}
