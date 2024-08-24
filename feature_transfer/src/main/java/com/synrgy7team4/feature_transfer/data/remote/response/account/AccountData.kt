package com.synrgy7team4.feature_transfer.data.remote.response.account


import com.google.gson.annotations.SerializedName

data class AccountData(
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class Accounts(
    @SerializedName("id")
    val id: String,
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userName")
    val userName: String
)



fun AccountData.toDomain(): com.synrgy7team4.feature_transfer.domain.model.Account{
    return com.synrgy7team4.feature_transfer.domain.model.Account(
        accountNumber =accountNumber,
        id = id,
        name = name
    )
}


fun Accounts.toDomain(): com.synrgy7team4.feature_transfer.domain.model.Accounts{
    return com.synrgy7team4.feature_transfer.domain.model.Accounts(
        id = id,
        accountNumber =accountNumber,
        balance = balance,
        userId = userId,
        userName = userName
    )
}

