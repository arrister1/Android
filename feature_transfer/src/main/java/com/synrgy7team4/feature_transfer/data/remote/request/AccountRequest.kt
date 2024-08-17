package com.synrgy7team4.feature_transfer.data.remote.request


import com.google.gson.annotations.SerializedName

data class AccountRequest(
    @SerializedName("additionalProp1")
    val additionalProp1: String,
    @SerializedName("additionalProp2")
    val additionalProp2: String,
    @SerializedName("additionalProp3")
    val additionalProp3: String
)

fun AccountRequest.toDomain(): com.synrgy7team4.feature_transfer.domain.model.AccountReq{
    return com.synrgy7team4.feature_transfer.domain.model.AccountReq(
        additionalProp1 =additionalProp1,
        additionalProp2 =additionalProp2,
        additionalProp3 = additionalProp3
    )
}